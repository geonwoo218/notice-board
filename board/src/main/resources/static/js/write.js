function deleteW(){ //noticeshow 글삭제버튼
    const deleteForm = document.getElementById("deleteWForm");

    if(window.confirm("정말 이 게시글을 삭제하시겠습니까?")){
        deleteForm.submit();
    }
}

function openComment(){
    console.log("openCommentF");
    var comment = document.getElementById("comments");
    var openComment = document.getElementById("openComment");
    if(openComment.value == "보기"){
        comment.style.flexGrow = 8;
        openComment.value = "닫기";
    }else if(openComment.value == "닫기"){
        comment.style.flexGrow = 0;
        openComment.value = "보기";
    }
}