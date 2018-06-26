function fn_updateSave()
{
	var bootstrapValidator;
	bootstrapValidator=$('#pageForm').data('bootstrapValidator');
	$("#updateuser").val($("#curruser").val());
	$("#updatedate").val($("#currdate").val());
	$("#initdate").val("1900-01-01");
	bootstrapValidator.validate();
	return bootstrapValidator.isValid();
}
function fn_submitWF()
{
	var bootstrapValidator;
	bootstrapValidator=$('#pageForm').data('bootstrapValidator');
	$("#inituser").val($("#curruser").val());
	$("#initdept").val($("#currdept").val());
	$("#initdate").val($("#currdate").val());
	bootstrapValidator.validate();
	return bootstrapValidator.isValid();
}