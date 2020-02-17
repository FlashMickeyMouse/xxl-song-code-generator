## Introduction

在xxl-code-generator的基础上实现了动态模板生成。

例如你可以填入这样的模板：

```xml
<#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
<#list classInfo.fieldList as fieldItem >
    <el-table-column prop="${fieldItem.fieldName}" label="${fieldItem.fieldComment}" align="center" show-overflow-tooltip/>
</#list>
</#if>
```

将为您生成
    

```html
<el-table-column prop="userId" label="用户ID" align="center" show-overflow-tooltip/>
<el-table-column prop="username" label="用户名" align="center" show-overflow-tooltip/>
<el-table-column prop="addtime" label="创建时间" align="center" show-overflow-tooltip/>
```

自由掌控！！

最新版本增加了对iview的支持！！


新版本增加了 select 语句转vo对象的功能

