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