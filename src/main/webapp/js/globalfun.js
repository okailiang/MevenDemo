/**
 * Created by oukailiang on 16/7/25.
 */
$(function(){
    $("#checkbox-all").click(function(){
        if(this.checked){
            $("input[type='checkbox']").prop("checked",true);
        }else{
            $("input[type='checkbox']").prop("checked",false);
        }

    });
});
