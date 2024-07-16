function deleteW(){ //noticeshow 글삭제버튼
    const deleteForm = document.getElementById("deleteWForm");

    if(window.confirm("정말 이 게시글을 삭제하시겠습니까?")){
        deleteForm.submit();
    }
}

