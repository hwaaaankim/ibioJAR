$(function(){
	if(!searchType || 
	searchType === 'name' || 
	searchType === 'business' || 
	searchType == 'none' || 
	searchType == 'email' ||
	searchType == 'username'){
		$('#phoneSearch').hide();
		$('#periodStart').hide();
		$('#periodEnd').hide();
	}else if(searchType==='period'){
		$('#textSearch').hide();
		$('#phoneSearch').hide();
	}else if(searchType === 'phone'){
		$('#textSearch').hide();
		$('#phoneSearch').hide();
	}
	$('#searchType').on('change',function(){
		if($('#searchType option:selected').attr('id')==='searchName' || 
		$('#searchType option:selected').attr('id') === 'searchUsername' ||
		$('#searchType option:selected').attr('id') === 'searchEmail' ||
		$('#searchType option:selected').attr('id') === 'searchBusiness' ||
		$('#searchType option:selected').attr('id') === 'searchBasic'){
			$('#phoneSearch').hide();
			$('#periodStart').hide();
			$('#periodEnd').hide();
			$('#textSearch').show();
			$('#textSearch input').val('');
		}else if($('#searchType option:selected').attr('id')==='searchPhone'){
			$('#textSearch').hide();
			$('#periodStart').hide();
			$('#periodEnd').hide();
			$('#phoneSearch').show();
			$('#phoneSearch input').val('');
		}else if($('#searchType option:selected').attr('id')==='searchPeriod'){
			$('#textSearch').hide();
			$('#phoneSearch').hide();
			$('#periodStart').show();
			$('#periodEnd').show();
			$('#startDate').val(new Date().toISOString().slice(0, 10));
			$('#endDate').val(new Date().toISOString().slice(0, 10));
		}
	});
	
});

const autoHyphen = (target) => {
	target.value = target.value
		.replace(/[^0-9]/g, '')
		.replace(/^(\d{0,3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3").replace(/(\-{1,2})$/g, "");
	}