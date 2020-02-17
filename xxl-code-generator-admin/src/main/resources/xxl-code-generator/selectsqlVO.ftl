<#if fieldInfos?exists && fieldInfos?size gt 0>
    <#list fieldInfos as fieldItem >
        <#if fieldItem.fieldComment?default("")?trim?length gt 1>
        /**
        * ${fieldItem.fieldComment}
        */
        @ApiModelProperty("${fieldItem.fieldComment}")
        </#if>
        private ${fieldItem.fieldClass} ${fieldItem.fieldName};

    </#list>
</#if>