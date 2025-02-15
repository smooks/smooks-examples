<#assign nextInterchangeControlNumber = (isa.interchangeControlNumber?number + 1)?string?left_pad(9, "0")>
<#assign interchangeDate = .now?string("yyMMdd")>
<#assign interchangeTime = .now?string("HHmm")>
<#assign functionalGroupDate = .now?string("yyyyMMdd")>
<#assign functionalGroupTime = .now?string("HHmmss")>

<interchange>
    <segment>
        <segmentId>ISA</segmentId>
        <dataElement>00</dataElement>
        <dataElement>          </dataElement>
        <dataElement>00</dataElement>
        <dataElement>          </dataElement>
        <dataElement>${isa.interchangeReceiverIdQualifier}</dataElement>
        <dataElement>${isa.interchangeReceiverId}</dataElement>
        <dataElement>${isa.interchangeSenderIdQualifier}</dataElement>
        <dataElement>${isa.interchangeSenderId}</dataElement>
        <dataElement>${interchangeDate}</dataElement>
        <dataElement>${interchangeTime}</dataElement>
        <dataElement>U</dataElement>
        <dataElement>00204</dataElement>
        <dataElement>${nextInterchangeControlNumber}</dataElement>
        <dataElement>1</dataElement>
        <dataElement>T</dataElement>
        <dataElement>&gt;</dataElement>
    </segment>
    <segment>
        <segmentId>GS</segmentId>
        <dataElement>FA</dataElement>
        <dataElement>${gs.applicationReceiverCode}</dataElement>
        <dataElement>${gs.applicationSenderCode}</dataElement>
        <dataElement>${functionalGroupDate}</dataElement>
        <dataElement>${functionalGroupTime}</dataElement>
        <dataElement>000000001</dataElement>
        <dataElement>X</dataElement>
        <dataElement>004010</dataElement>
    </segment>
    <segment>
        <segmentId>ST</segmentId>
        <dataElement>997</dataElement>
        <dataElement>0001</dataElement>
    </segment>
    <segment>
        <segmentId>AK1</segmentId>
        <dataElement>${st.transactionSetIdentifier}</dataElement>
        <dataElement>${gs.groupControlNumber}</dataElement>
    </segment>
    <segment>
        <segmentId>AK9</segmentId>
        <dataElement>${ackStatus}</dataElement>
    </segment>
    <segment>
        <segmentId>SE</segmentId>
        <dataElement>4</dataElement>
        <dataElement>0001</dataElement>
    </segment>
    <segment>
        <segmentId>GE</segmentId>
        <dataElement>1</dataElement>
        <dataElement>000000001</dataElement>
    </segment>
    <segment>
        <segmentId>IEA</segmentId>
        <dataElement>1</dataElement>
        <dataElement>${nextInterchangeControlNumber}</dataElement>
    </segment>
</interchange>