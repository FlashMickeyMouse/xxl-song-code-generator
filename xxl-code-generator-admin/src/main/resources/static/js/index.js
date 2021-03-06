$(function () {

    /**
     * 初始化 table sql
     */
    var tableSqlIDE;

    function initTableSql() {
        tableSqlIDE = CodeMirror.fromTextArea(document.getElementById("tableSql"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-sql",
            lineWrapping: false,
            readOnly: false,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        tableSqlIDE.setSize('auto', 'auto');
    }

    initTableSql();


    /**
     * 初始化 select sql
     */
    var selectSqlIDE;

    function initSelectSql() {
        selectSqlIDE = CodeMirror.fromTextArea(document.getElementById("selectSql"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-sql",
            lineWrapping: false,
            readOnly: false,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        selectSqlIDE.setSize('auto', 'auto');
    }

    initSelectSql();


    var trashyJsonIDE;
    function trashyJson() {
        trashyJsonIDE = CodeMirror.fromTextArea(document.getElementById("trashyJson"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-sql",
            lineWrapping: false,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        trashyJsonIDE.setSize('auto', 'auto');
    }

    trashyJson();

    /**
     * 初始化templateContent
     */
    var templateContentIDE;

    function templateContent() {
        templateContentIDE = CodeMirror.fromTextArea(document.getElementById("templateContent"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: false,
            readOnly: false,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        templateContentIDE.setSize('auto', 'auto');
    }

    templateContent();

    /**
     * 初始化 code area
     */

    var customcode_ide;
    var controller_ide;
    var service_ide;
    var service_impl_ide;
    var dao_ide;
    var mybatis_ide;
    var model_ide;
    var iview_ide;
    var selectSql_ide;

    function initCodeArea() {


        // customcode_ide
        customcode_ide = CodeMirror.fromTextArea(document.getElementById("customcode_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: true,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        customcode_ide.setSize('auto', 'auto');

        // controller_ide
        controller_ide = CodeMirror.fromTextArea(document.getElementById("controller_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: true,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        controller_ide.setSize('auto', 'auto');

        // service_ide
        service_ide = CodeMirror.fromTextArea(document.getElementById("service_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: true,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        service_ide.setSize('auto', 'auto');

        // service_impl_ide
        service_impl_ide = CodeMirror.fromTextArea(document.getElementById("service_impl_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: true,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        service_impl_ide.setSize('auto', 'auto');

        // dao_ide
        dao_ide = CodeMirror.fromTextArea(document.getElementById("dao_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: true,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        dao_ide.setSize('auto', 'auto');

        // mybatis_ide
        mybatis_ide = CodeMirror.fromTextArea(document.getElementById("mybatis_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/html",
            lineWrapping: true,
            readOnly: true
        });
        mybatis_ide.setSize('auto', 'auto');

        // model_ide
        model_ide = CodeMirror.fromTextArea(document.getElementById("model_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: true,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        model_ide.setSize('auto', 'auto');
        // iview_ide
        iview_ide = CodeMirror.fromTextArea(document.getElementById("iview_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: true,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        iview_ide.setSize('auto', 'auto');


        // selectSql_ide
        selectSql_ide = CodeMirror.fromTextArea(document.getElementById("selectSql_ide"), {
            lineNumbers: true,
            matchBrackets: true,
            mode: "text/x-java",
            lineWrapping: true,
            readOnly: true,
            foldGutter: true,
            gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"]
        });
        selectSql_ide.setSize('auto', 'auto');
    }

    initCodeArea();

    /**
     * 生成代码
     */
    $('#codeGenerate').click(function () {

        var tableSql = tableSqlIDE.getValue();

        $.ajax({
            type: 'POST',
            url: base_url + "/codeGenerate",
            data: {
                "tableSql": tableSql
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    layer.open({
                        icon: '1',
                        content: "代码生成成功",
                        end: function (layero, index) {

                            controller_ide.setValue(data.data.controller_code);
                            controller_ide.setSize('auto', 'auto');

                            service_ide.setValue(data.data.service_code);
                            service_ide.setSize('auto', 'auto');

                            service_impl_ide.setValue(data.data.service_impl_code);
                            service_impl_ide.setSize('auto', 'auto');

                            dao_ide.setValue(data.data.dao_code);
                            dao_ide.setSize('auto', 'auto');

                            mybatis_ide.setValue(data.data.mybatis_code);
                            mybatis_ide.setSize('auto', 'auto');

                            model_ide.setValue(data.data.model_code);
                            model_ide.setSize('auto', 'auto');

                            iview_ide.setValue(data.data.iview_code);
                            iview_ide.setSize('auto', 'auto');

                        }
                    });
                } else {
                    layer.open({
                        icon: '2',
                        content: (data.msg || '代码生成失败')
                    });
                }
            }
        });

    });




    /**
     * 生成 select sql 代码
     */
    $('#selectSqlParseGenerate').click(function () {

        var selectSql = selectSqlIDE.getValue();

        $.ajax({
            type: 'POST',
            url: base_url + "/selectSqlParseGenerate",
            data: {
                "selectSql": selectSql
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    layer.open({
                        icon: '1',
                        content: "代码生成成功",
                        end: function (layero, index) {

                            selectSql_ide.setValue(data.data.selectsqlVO_code);
                            selectSql_ide.setSize('auto', 'auto');

                        }
                    });
                } else {
                    layer.open({
                        icon: '2',
                        content: (data.msg || '代码生成失败')
                    });
                }
            }
        });

    });



    /**
     * 生成代码
     */
    $('#customCodeGenerate').click(function () {

        var tableSql = tableSqlIDE.getValue();
        var customftl = templateContentIDE.getValue()

        $.ajax({
            type: 'POST',
            url: base_url + "/codeGenerateCustom",
            data: {
                "tableSql": tableSql,
                "customftl": customftl
            },
            dataType: "json",
            success: function (data) {
                if (data.code == 200) {
                    layer.open({
                        icon: '1',
                        content: "代码生成成功",
                        end: function (layero, index) {

                            customcode_ide.setValue(data.data.custom_code);
                            customcode_ide.setSize('auto', 'auto');

                        }
                    });
                } else {
                    layer.open({
                        icon: '2',
                        content: (data.msg || '代码生成失败')
                    });
                }
            }
        });

    });


});