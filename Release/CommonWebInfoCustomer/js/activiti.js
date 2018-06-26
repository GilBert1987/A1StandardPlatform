function dyniframesize(down) {
	var pTar = null;
	if (document.getElementById) {
		pTar = document.getElementById(down);
	} else {
		eval('pTar = ' + down + ';');
	}
	if (pTar && !window.opera) {
		pTar.style.display = "block";
		pTar.height = document.body.scrollHeight-50;
		pTar.width = document.body.scrollWidth-50;
	}
}