About
=====

Customising an EDIFACT DFDL schema pack is a frequent requirement. Apart from the UN/EDIFACT releases that the EDIFACT cartridge may not support, many organisations have their own flavour of EDIFACT. This example demonstrates how the EDI specialist can adapt an EDIFACT DFDL schema pack to fit the implementation guide they are following. In particular, this example customises the D03B EDIFACT schema pack to support the _OUTORD_ message type from the KEDIFACT implementation guide (a South Korean standard based on EDIFACT). The steps for overriding the schema pack were:

1. Creating a directory in the example's classpath (i.e., `src/main/resources`) named `kedifact` 
2. Extracting the `EDIFACT-Messages.dfdl.xsd` and `EDIFACT-Segments.dfdl.xsd` DFDL schemas from the [D03B EDIFACT schema pack archive](https://repo1.maven.org/maven2/org/smooks/cartridges/edi/edifact-schemas/2.0.1/edifact-schemas-2.0.1-d03b.jar) (you should use a schema pack that closely matches your requirements in terms of segments and data elements)
3. Copying the extracted schemas to the `kedifact` directory
4. Tweaking the `EDIFACT-Messages.dfdl.xsd` such that the `INVOIC` message type in now `OUTORD` (one could have just as easily added a new message type named `OUTORD` instead of renaming the `INVOIC` message type)
5. Referencing the custom DFDL schema from the Smooks config:

```xml
<?xml version="1.0"?>
<smooks-resource-list xmlns="https://www.smooks.org/xsd/smooks-2.0.xsd"
                      xmlns:edifact="https://www.smooks.org/xsd/smooks/edifact-2.0.xsd"
                      xmlns:core="https://www.smooks.org/xsd/smooks/smooks-core-1.6.xsd">

    <core:smooks filterSourceOn="/Interchange">
        <core:action>
            <core:inline>
                <core:replace/>
            </core:inline>
        </core:action>
        <core:config>
            <smooks-resource-list>
                <edifact:unparser schemaUri="/kedifact/EDIFACT-Messages.dfdl.xsd" unparseOnNode="*">
                    <edifact:messageTypes>
                        <edifact:messageType>OUTORD</edifact:messageType>
                    </edifact:messageTypes>
                </edifact:unparser>
            </smooks-resource-list>
        </core:config>
    </core:smooks>

</smooks-resource-list>
```

### How to run?

1. `mvn clean package`
2. `mvn exec:exec`