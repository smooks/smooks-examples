<segment segmentId="${.vars["segment"].segmentId}">
    <#list .vars["segment"].dataElement as dataElement>
         <dataElement>${dataElement}</dataElement>
    </#list>
</segment>