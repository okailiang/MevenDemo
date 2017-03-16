var showVoucherTable = function (data) {
    var len = data.length;
    var html = "";
    for (var i = 0; i < len; i++) {

        html += "<tr>" + "<td><input type='checkbox' value='" + data[i].id + "' /></td>" +
            "<td>" + data[i].id + "</td>";
        html += "<td>" + data[i].voucherNo + "</td>";
        html += "<td>" + data[i].amount + "</td>";
        html += "<td>" + data[i].activeId + "</td>";
        html += "<td>" + (data[i].bacthNo == undefined ? "" : data[i].bacthNo) + "</td>";
        html += "<td>" + data[i].consumerType + "</td>";
        html += "<td>" + data[i].activeName + "</td>";
        html += "<td>" + data[i].activeType + "</td>";
        html += "<td>" + data[i].status + "</td>";
        html += "<td>" + data[i].voucherBeginTime + "</td>";
        html += "<td>" + data[i].voucherEndTime + "</td>";
        html += "<td>" + data[i].activeStartTime + "</td>";
        html += "<td>" + data[i].activeEndTime + "</td>";
        html += "<td>" + (data[i].activeImageUrl == undefined ? "" : data[i].activeImageUrl) + "</td>";
        html += "<td>" + data[i].activeRule + "</td>";
        html += "<td>" + data[i].full + "</td>";
        html += "<td>" + data[i].type + "</td>";
        html += "<td>" + data[i].useTimes + "</td>";
        html += "<td>" + data[i].version + "</td>";
        html += "<td>" + data[i].createTime + "</td>";
        html += "<td>" + data[i].lastModified + "</td>";
        html += "<td>" + data[i].modifiedBy + "</td>";
        html += "<td>" + data[i].businessType + "</td>";
        html += "<td>" + (data[i].phoneNum == undefined ? "" : data[i].phoneNum) + "</td>";
        html += "<td>" + data[i].cityId + "</td>";
        html += "<td>" + data[i].strategyCount + "</td></tr>";
    }
    var voucherBody = $("#voucher-table-body");
    voucherBody.empty();
    voucherBody.append(html);
};


$(function () {
    $("#voucher-table").ready(function () {
        $.ajax({
            type: 'get',
            url: 'getTopThreeVouchers.do',
            dataType: "json",
            data: {},
            cache: false,
            success: function (data) {
                if (data.length > 0) {
                    showVoucherTable(data);
                }
            },
            error: function (data) {
            }
        });
    });

    $("#search-voucher").click(function () {

        var id = $("#voucher-id").val();
        if (id == "") {
            alert("请输入Id号！");
            return false;
        }
        $.ajax({
            type: 'get',
            url: 'getVoucherById.do',
            dataType: "json",
            data: {
                id: id
            },
            cache: false,
            success: function (data) {
                if (data == null) {
                    $("#voucher-table-body").empty();
                    $("#voucher-table-body").append('<tr><td colspan="6" ' +
                        'style="text-align:center;color:red">没有找到该条数据！</td> </tr>');
                    return false;
                }
                var arr = [];
                arr.push(data)
                if (arr.length > 0) {
                    showVoucherTable(arr);
                }
            },
            error: function (data) {
            }
        });
    });

    $("#add-voucher").click(function () {
        $.ajax({
            type: 'post',
            url: 'insertVoucher.do',
            dataType: "json",
            data: {},
            cache: false,
            success: function (data) {
                window.location.href = "voucherList.do?id=0";
            },
            error: function (data) {
            }
        });
    });

    $("#delete-voucher").click(function () {
        var ids = "";

        $("input[type='checkbox']").each(function () {
            if ($(this).is(":checked")&&this.id!="checkbox-all") {
                ids += $(this).val() + ",";
            }
        });
        if (ids == "") {
            alert("请选择要删除的数据！");
            return false;
        }
        $.ajax({
            type: 'get',
            url: 'deleteVouchers.do',
            dataType: "json",
            data: {
                ids: ids
            },
            cache: false,
            success: function (data) {
                window.location.href = "voucherList.do?id=0";
            },
            error: function (data) {
                alert("删除失败！");
            }
        });
    });
});
