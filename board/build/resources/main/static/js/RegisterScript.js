var regForm = document.getElementById("regForm");
var FName = regForm.name.value;
const namePatten = /^[a-zA-Zㄱ-ㅎㅏ-ㅣ가-힣0-9_()]+$/;
var FPwd = regForm.pwd.value;
//const pwdPatten = /^(?!.*\s)(?!.*[ㄱ-ㅎㅏ-ㅣ가-힣]).{8,}$/;

var isName = false; //이름 중복체크
var emailNum = false; //인증번호확인체크

//이름 중복확인 버튼
function check_name() {

    //이름 유효성
    if(namePatten.test(FName)){
        alert("이름이 잘못됐습니다. 변경해 주세요.");
        regForm.name.focus();
    }

    isName=false;
    console.log("js 이름 체크")
    let username = document.getElementById('name').value;
    $.ajax({
        type: 'POST',
        url: '/submit_username',
        contentType: 'application/json',
        dataType: 'text',
        data: username,
        success: function(result) {
            if(result == "yes"){
                alert("중복입니다.");
            }else{
                alert("사용 가능합니다.")
                isName = true;
            }
        },
        error: function(request, status, error) {
            console.log(request, status, error);

        }
    });
    console.log("js isName: "+isName);
}

//인증번호 받기 버튼
function emailSend(){

console.log("js 이메일 보내기");
    let mail = document.getElementById('email').value;
    console.log("mail:"+mail);
    alert("인증번호를 발송했습니다.");
    $.ajax({
       type: 'POST',
       url: '/mailSend',
       contentType: 'application/json',
       dataType: 'text',
       data: mail,
       success: function(result) {
            if(result == "Success"){
                document.getElementById('email').disabled = true;
            }else if(result == "Fail"){
                document.getElementById('email').disabled = false;
                alert("메일 전송 실패");
            }
       },
       error: function(request, status, error) {
           alert("메일 전송 실패");

       }
    });
}

//인증번호 확인 버튼
function mailCheck(){
    if(regForm.emailCheck.value == "") {
        alert("인증번호 입력해줘요");
        regForm.emailCheck.focus();
        return;
    }
console.log("인증번호 체크");
    let userNumber = document.getElementById('emailCheck').value;
    console.log("js mailCheck: "+userNumber);
    $.ajax({
       type: 'POST',
       url: '/mailCheck',
       contentType: 'application/json',
       dataType: 'text',
       data: userNumber,
       success: function(result) {
            if(result == "true"){
                alert("인증 성공");
                emailNum =true;
            }else{
                alert("인증번호가 다릅니다.");
                emailNum = false;
            }
       },
       error: function(request, status, error) {
           console.log("js result:"+result);
       }
    });
}

//폼 제출
function submitForm(){
    document.getElementById('email').disabled = false;

    if(regForm.name.value == "") {
        alert("이름을 입력해주세요");
        regForm.name.focus();
        return;
    }
    //이름 중복확인버튼 여부
    if(!isName){
        alert("중복확인 해주세요");
        regForm.name.focus();
        return;
    }

    if(regForm.email.value == "") {
            alert("메일을 입력해주세요");
            regForm.email.focus();
            return;
    }
    //인증번호확인버튼 여부
    if(!emailNum){
        alert("인증번호를 인증 해주세요");
        regForm.emailCheck.focus();
        return;
    }


    //비밀번호 입력
    if(regForm.pwd.value == ""){
        alert("비밀번호를 입력해주세요")
        regForm.pwd.focus();
        return;
    }
    //비밀번호 유효성
    if(regForm.pwd.value.includes(" ")) {
        alert("비밀번호에는 공백이 들어갈 수 없습니다.");
        regForm.pwd.focus();
        return;
    }

    if(regForm.pwd.value.length < 8) {
        alert("8자 이상이여야합니다.");
        regForm.pwd.focus();
        return;
    }

    //비밀번호 확인 검사
    if(regForm.pwd.value !== regForm.confirm_password.value){
        alert("비밀번호확인이 비밀번호와 다릅니다.");
        return;
    }


    if(isName && emailNum){
        try{
            alert("회원가입이 되었습니다.");
            regForm.submit();
        }catch(e){
            alert("회원가입이 안됐습니다.");
            console.log(e);
        }

        document.getElementById('email').disabled = true;

    }

    document.getElementById("profileModal").addEventListener('click',()=>{
        document.getElementById("profileModal").style.display = "block";
        const imageList = document.querySelectorAll('#imageList li img');
        let selectedImageIndex = null;

        imageList.forEach((img, index) => {
            img.addEventListener('click', () => {
                // Remove selected class from all images
                imageList.forEach(img => img.classList.remove('selected'));
                // Add selected class to the clicked image
                img.classList.add('selected');
                // Set selected image index
                selectedImageIndex = index + 1; // Index starts from 1
            });
        });
    });

    document.getElementById('confirmBtn').addEventListener('click', () => {
        if (selectedImageIndex !== null) {
            console.log('Selected Image Index:', selectedImageIndex);
            // Here you can handle the selected image index value as needed
        } else {
            alert('이미지를 선택해주세요.');
        }
    });

    document.getElementById('pModalCloseBtn').addEventListener('click', () => {
        document.getElementById('profileModal').style.display = 'none';
    });
}