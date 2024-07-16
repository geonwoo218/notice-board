function search(){
    var searchTitle = document.getElementById("searchTitle");
    if(searchTitle){
       window.location.href = `/search?title=${searchTitle.value}`;
    }else{
        alert("검색할 단어를 넣어주세요");
    }
}

document.getElementById('searchTitle').addEventListener('keypress', (e)=>{
    if(e.keyCode == 13) search();
});


function openComment(n){
    var commentModal = document.getElementById("commentModal");
    var commentBg = document.getElementById("commentBg");
    if(n){
        commentModal.style.display = "block";
        commentBg.style.display = "block";
    }else{
        commentModal.style.display = "none";
        commentBg.style.display = "none";
    }
}

function cWriteWarning(){
    alert("로그인 후 이용 가능")
    if(confirm("로그인 페이지로 이동 하겠습니까?")){
        location.href="/login";
    }else{
        alert("ㅇㅋ");
    }
}