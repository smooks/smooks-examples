About
=====

This is the Stock Tick [Complex Event Processing (CEP)](https://en.wikipedia.org/wiki/Complex_event_processing) example copied from the Drools project and adapted to Smooks. It demonstrates Smooks feeding events to [jBPM](https://www.jbpm.org/) to accomplish CEP. More generally, it illustrates the use of the Smooks `BeanContextLifecycleObserver` API for listening and acting upon bean lifecycle events (e.g., bean creation) within the Smooks execution bean context. The application listens to the creation of `StockTick` instances and feeds them directly to a jBPM process while Smooks filters the input stream.

The original Stock Tick CEP example fed the stock tick events to Drools from a CSV file. Said example wrapped the CSV file stream in a `StockTickPersister` class and used basic JDK classes to parse the CSV records and produce the `StockTick` instances which fired the CEP rules. While good enough when the source is simple to unmarshal, this low-level approach breaks down when the event data source becomes complex. This led the Smooks community to customise the example in order to highlight Smooks's ability to generate events from the source and feed them to a Business Process Management (BPM) engine which is jBPM.

This example keeps the structure of the source the same as in the original example: it does not show the processing of complex data streams, more complex than the Stock Tick CSV that is. It does, however, show Smooks feeding POJO events to jBPM. Processing complex data structures would logically entail a different Smooks configuration. The integration with jBPM would not be any different and this is the main point of this example.

### Event Source

The event source is the stock tick sample file provided in the Drools Stock Tick CEP example (`src/main/resources/stocktickstream.dat`):

```
0;SAP;$73.67
0;RHT;$60.69
0;JAVA;$59.63
0;IBM;$110.38
0;GOOG;$118.18
0;YHOO;$85.31
0;ORCL;$70.57
0;MSFT;$70.65
0;JAVA;$54.33
808;IBM;$109.79
1753;RHT;$63.06
2227;RHT;$59.29
... 
...
```

### Smooks Configuration

The Smooks configuration file is found in the root directory of the example (`smooks-config.xml`). It configures the `{https://www.smooks.org/xsd/smooks/csv-1.7.xsd}reader` and the Java bean binding:

```xml
<smooks-resource-list xmlns="https://www.smooks.org/xsd/smooks-2.0.xsd"
                      xmlns:csv="https://www.smooks.org/xsd/smooks/csv-1.7.xsd"
                      xmlns:jb="https://www.smooks.org/xsd/smooks/javabean-1.6.xsd">

    <!--
        Split out the individual stock tick records using a <csv:reader>.  Could also use
        a <regex:reader>...
    -->
    <csv:reader fields="time,symbol,price" separator=";"
                rootElementName="stockTicks" recordElementName="stockTick" />

    <!--
        Bind the stock <stockTick> fields into the StockTick object instance...
    -->
    <jb:value beanId="timestamp" data="/stockTicks/stockTick/time" decoder="Long" />
    <jb:bean beanId="stockTick" class="org.smooks.examples.drools.model.StockTick" createOnElement="/stockTicks/stockTick">
        <jb:expression property="timestamp">PTIME.startMillis + timestamp</jb:expression>
        <jb:value property="symbol" data="#/symbol" />
        <jb:value property="price" data="#/price" decoder="Double">
            <jb:decodeParam name="type">CURRENCY</jb:decodeParam>
            <jb:decodeParam name="locale">en_US</jb:decodeParam>
        </jb:value>
        <jb:expression property="str">this.createString()</jb:expression>
    </jb:bean>

</smooks-resource-list>
```

`csv:reader` configures Smooks to create a stream of SAX events from the stock tick CSV records. The `{https://www.smooks.org/xsd/smooks/javabean-1.6.xsd}value` and `{https://www.smooks.org/xsd/smooks/javabean-1.6.xsd}bean` bindings create the `StockTick` objects from the event stream. These `StockTick` objects are eventually fed to jBPM as events. Smooks will create a `StockTick` instance for each stock tick record in the CSV stream and bind the `StockTick` instance to the bean context under the bean ID of `stockTick`. It is worth emphasising that only a single instance of the `StockTick` class will live in the execution bean context at any one time.

### Listening to New Stock Tick Objects

The main insight behind this integration is the use of the Smooks `BeanContextLifecycleObserver` API in `SmooksEventSource`:

```java
public class SmooksEventSource implements EventSource {

   private final Smooks smooks;
   private final BlockingQueue<StockTick> inQueue = new SynchronousQueue<>();

   public SmooksEventSource() throws IOException, SAXException {
      smooks = new Smooks(new DefaultApplicationContextBuilder().withClassLoader(this.getClass().getClassLoader()).build());
      smooks.addResourceConfigs("./smooks-config.xml");
      smooks.getApplicationContext().addBeanContextLifecycleObserver(new BeanContextObserver());
   }

   public void processFeed(final InputStream tickerFeed) {
      new Thread(() -> smooks.filterSource(new StreamSource<>(tickerFeed))).start();
   }

   public boolean hasNext() {
      // Returning true because otherwise it will exit immediately...
      return true;
   }

   public Event<?> getNext() {
      try {
         StockTick stockTick = inQueue.take();
         return new EventImpl<>(stockTick.getTimestamp(), stockTick);
      } catch (InterruptedException e) {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * Listen for StockTicker beans being created in Smooks BeanContexts and add them to the
    * StockTick inQueue...
    */
   private class BeanContextObserver implements BeanContextLifecycleObserver {

      public void onBeanLifecycleEvent(BeanContextLifecycleEvent event) {
         if (event.getLifecycle() == BeanLifecycle.END_FRAGMENT) {
            if (event.getBeanId().getName().equals("stockTick")) {
               try {
                  inQueue.put((StockTick) event.getBean());
               } catch (InterruptedException e) {
                  e.printStackTrace();
               }
            }
         }
      }
   }
}
```

`SmooksEventSource` implements `BeanContextLifecycleObserver` in an inner class called `BeanContextObserver`. An instance of `BeanContextObserver` is attached to the Smooks application context which allows `SmooksEventSource` to listen on every `StockTick` object as it is created by the Smooks runtime. `SmooksEventSource` then directly supplies each `StockTick` instance as an event to jBPM. In other words, a direct feed of `StockTick` instances from Smooks to jBPM is established, with no batching of events. It means you can efficiently process both huge and hierarchical data streams such as XML, EDIFACT, and so on; not just simple flat files. 

The process for connecting the event source to the CEP engine with Smooks can be boiled down to these three steps:

1. Convert the CSV data stream to a SAX event stream.
2. Create a `StockTick` instance for each stock tick record event: a `StockTick` is bound to the execution bean context under the bean ID `stockTick`.
3. Listen for new `stockTick` objects with `BeanContextObserver` and feed them to the BPM engine.

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`