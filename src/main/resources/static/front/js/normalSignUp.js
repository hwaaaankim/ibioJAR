let usernameCheck = false;
let passwordCheck = false;
let emailCheck = false;
let phoneCheck = false;
let privacyCheck = true;

$(function(){

	
	$('#username').on('change', function(){
		$.ajax({
			type:'POST', 
			url:'/usernameCheck',
			data:{
				username:$(this).val(),
			},success:function(result){
				if(result){
					$('#username-error-message').text('# 중복된 아이디 입니다.');
					$('#username-error-message').focus();
					usernameCheck = false;
				}else{
					$('#username-error-message').text('');
					usernameCheck = true;
				}
				
			},error:function(request, status, request){
			}
		});
	});
	
	$('#passwordCheck').attr('disabled', true);
	$('#password').on('change', function(){
		$('#passwordCheck').attr('disabled', false);
	});
	
	$('#passwordCheck').on('change', function(){
		if($('#password').val() === $('#passwordCheck').val()){
			$('#passswordcheck-error-message').text('');
			passwordCheck = true;
		}else{
			$('#passswordcheck-error-message').text('# 비밀번호를 정확하게 입력 해 주세요.');
			passwordCheck = false;
		}
	});
	
	$('#email').on('change', function(){
		$.ajax({
			type:'POST', 
			url:'/emailCheck',
			data:{
				email:$(this).val(),
			},success:function(result){
				console.log(result);
				if(result){
					$('#email-error-message').text('# 중복된 이메일 입니다.');
					emailCheck = false;
				}else{
					$('#email-error-message').text('');
					emailCheck = true;
				}
				
			},error:function(request, status, request){
			}
		});
	});
	
	$('#phone').on('change', function(){
		$.ajax({
			type:'POST', 
			url:'/phoneCheck',
			data:{
				phone:$(this).val(),
			},success:function(result){
				if(result){
					$('#phone-error-message').focus();
					$('#phone-error-message').text('# 중복된 휴대폰 번호 입니다.');
					phoneCheck = false;
				}else{
					$('#phone-error-message').text('');
					phoneCheck = true;
				}
				
			},error:function(request, status, request){
			}
		});
	});
	
	$('#agree').on('change', function(){
		if($(this).prop('checked')){
			$('#privacy-error-message').text('');
			privacyCheck = true;
		}else{
			$('#privacy-error-message').focus();
			$('#privacy-error-message').text('# 동의하지 않는 경우 회원가입이 불가능합니다.');
			privacyCheck = false;
		}
	});
	
	
});
function signUpCheck(){
	let signupCheck = usernameCheck&&passwordCheck&&emailCheck&&phoneCheck&&privacyCheck;
	if(signupCheck){
		var result = confirm('회원가입을 진행 하시겠습니까?');
		if(result){
			return true;
		}else{
			return false;
		}
	}else{
		return false;
	}
}


















