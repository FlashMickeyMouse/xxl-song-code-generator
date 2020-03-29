<template>
    <div>
        <Card>
            <Form :model="queryConditions">
                <Row :gutter="32">

                    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                        <#list classInfo.fieldList as fieldItem >
                            <Col span="4">
                            <FormItem label="${fieldItem.fieldComment}" label-position="top">
                                <Input v-model="queryConditions.${fieldItem.fieldName}"
                                       placeholder="请输入${fieldItem.fieldComment}"/>
                            </FormItem>
                            </Col>
                        </#list>
                    </#if>


                </Row>
            </Form>
            <Row :gutter="32">
                <Col span="1">
                <Button type="primary" @click="search">查询</Button>
                </Col>
                <Col span="1">
                <Button type="primary" @click="restSearch">重置</Button>
                </Col>
                <Col span="1">
                <Button type="primary" @click="isAdd = true">新增</Button>
                </Col>
            </Row>

            <Table ref="tables" :data="tableData" :columns="columns" @on-sort-change="handleSort"
                   style="margin-top: 10px; margin-bottom: 10px; "/>
            <Page
                    :total="total"
                    :page-size="pageSize"
                    :current="pageNum"
                    show-total
                    @on-change="changepage"
            />
        </Card>
        <Drawer
                title="Create"
                v-model="isAdd"
                width="720"
                :mask-closable="false"
                :styles="styles"
                @on-visible-change="restForm"
        >
            <Form :model="${classInfo.className?uncap_first} " :rules="ruleValidate"
                  ref="${classInfo.className?uncap_first}form">
                <Row :gutter="32">

                    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                        <#list classInfo.fieldList as fieldItem >
                            <Col span="12">
                            <FormItem label="${fieldItem.fieldComment}" prop="${fieldItem.fieldName}" label-position="top">
                                <Input v-model="${classInfo.className?uncap_first}.${fieldItem.fieldName}" placeholder="请输入${fieldItem.fieldComment}"/>
                            </FormItem>
                            </Col>
                        </#list>
                    </#if>


                </Row>


            </Form>
            <div class="demo-drawer-footer">
                <Button type="primary" @click="handleSubmit('${classInfo.className?uncap_first}form')">保存</Button>
            </div>
        </Drawer>
    </div>
</template>

<script>
    import axios from "@/libs/api.request";

    export default {
        name: "${classInfo.className?uncap_first}",
        data() {
            let columns = [
                <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                <#list classInfo.fieldList as fieldItem >
                    {title: "${fieldItem.fieldComment}", key: "${fieldItem.fieldName}"},
                </#list>
                </#if>

                {
                    title: "操作",
                    key: "action",
                    width: 150,
                    align: "center",
                    render: (h, params) => {
                        return h("div", [
                            h(
                                "Button",
                                {
                                    props: {
                                        type: "primary",
                                        size: "small"
                                    },
                                    style: {
                                        marginRight: "5px"
                                    },
                                    on: {
                                        click: () => {
                                            console.log(params);
                                            axios
                                                .request({
                                                    url: `${classInfo.className?uncap_first}/${r'${params.row.id}'}`,
                                                    method: "get"
                                                })
                                                .then(res => {
                                                    console.log(res.data.data);
                                                    this.${classInfo.className?uncap_first} = res.data.data;
                                                    this.isAdd = true;
                                                });
                                        }
                                    }
                                },
                                "编辑"
                            ),
                            h(
                                "Button",
                                {
                                    props: {
                                        type: "error",
                                        size: "small"
                                    },
                                    on: {
                                        click: () => {
                                            this.$Modal.confirm({
                                                title: "提示",
                                                content: "<p>您确定要删除吗？</p>",
                                                onOk: () => {
                                                    this.handleDel(params.row.id);
                                                },
                                                onCancel: () => {
                                                }
                                            });
                                        }
                                    }
                                },
                                "删除"
                            )
                        ]);
                    }
                }
            ];


            return {
                total: 0,
                pageNum: 1,
                pageSize: 10,
                queryConditions: {},
                ruleValidate: {},
                styles: {
                    height: "calc(100% - 55px)",
                    overflow: "auto",
                    paddingBottom: "53px",
                    position: "static"
                },
                isAdd: false,
                columns: columns,
                tableData: [],
            ${classInfo.className?uncap_first}:
            {
                <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                    <#list classInfo.fieldList as fieldItem >
                        ${fieldItem.fieldName}: "",
                    </#list>
                </#if>
            },
            selectData: []
        };
        },
        created() {
            this.getAllForSelect();
            this.get${classInfo.className?uncap_first}(this.pageNum, 10);
        },
        methods: {
            get${classInfo.className?uncap_first}(page, size) {
                axios
                    .request({
                        url: `${classInfo.className?uncap_first}/${r'${page}'}/${r'${size}'}`,
                        method: "post",
                        data: this.queryConditions
                    })
                    .then(res => {
                        console.log(res.data.data);
                        this.tableData = res.data.data.records;
                        this.total = res.data.data.total;
                    });
            },
            handleSort(data) {
                console.log(data);
            },
            getAllForSelect() {
                axios
                    .request({
                        url: `${classInfo.className?uncap_first}`,
                        method: "get"
                    })
                    .then(res => {
                        console.log(res.data.data);
                        this.selectData = res.data.data;
                    });
            },
            restForm(isShow) {
                if (isShow) {
                    this.getAllForSelect();
                } else {
                    console.log(isShow);
                    this.${classInfo.className?uncap_first} = {
                    <#if classInfo.fieldList?exists && classInfo.fieldList?size gt 0>
                    <#list classInfo.fieldList as fieldItem >
                    ${fieldItem.fieldName}: "",
                    </#list>
                    </#if>
                    };
                }
            },
            handleSubmit(name) {
                this.$refs[name].validate(valid => {
                    if (valid) {
                        let id = this.${classInfo.className?uncap_first}.id;
                        console.log(id);
                        if (!id) {
                            axios
                                .request({
                                    url: `${classInfo.className?uncap_first}`,
                                    method: "post",
                                    data: this.${classInfo.className?uncap_first}
                                })
                                .then(res => {
                                    if (res.data.flag) {
                                        alert("保存成功");
                                        this.isAdd = false;
                                        this.get${classInfo.className?uncap_first}(1, 10);
                                    } else {
                                        alert(res.data.message);
                                    }
                                });
                        } else {
                            axios
                                .request({
                                    url: `${classInfo.className?uncap_first}/${r'${id}'}`,
                                    method: "put",
                                    data: this.${classInfo.className?uncap_first}
                                })
                                .then(res => {
                                    if (res.data.flag) {
                                        alert("保存成功");
                                        this.isAdd = false;
                                        this.get${classInfo.className?uncap_first}(1, 10);
                                    } else {
                                        alert(res.data.message);
                                    }
                                });
                        }
                    } else {
                        this.$Message.error("请检查表单!");
                    }
                });
            },
            handleDel(id) {
                // alert(idCard);
                axios
                    .request({
                        url: `${classInfo.className?uncap_first}/${r'${id}'}`,
                        method: "delete"
                    })
                    .then(res => {
                        if (res.data.flag) {
                            alert("删除成功");
                            this.get${classInfo.className?uncap_first}(1, 10);
                        } else {
                            alert(res.data.message);
                        }
                    });
            },
            changepage(pageNum) {
                this.pageNum = pageNum;
                this.get${classInfo.className?uncap_first}(this.pageNum, this.pageSize);
            },
            search() {
                this.get${classInfo.className?uncap_first}(1, 10);
            },
            restSearch() {
                this.queryConditions = {};
                this.get${classInfo.className?uncap_first}(1, 10);
            }
        }
    };
</script>

<style>
</style>
