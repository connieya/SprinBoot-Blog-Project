let index={
	init:function(){
		$("#btn-save").on("click",()=>{ //화살표 함수를 쓰는이유 this를 바인딩 하기 위해서
			this.save();
		});
		$("#btn-delete").on("click",()=>{ //화살표 함수를 쓰는이유 this를 바인딩 하기 위해서
			this.deleteByid();
		});
	}
	,
	save:function(){
		//alert('user의 save함수 호출 됨')
		
		let data ={
			title: $("#title").val(),
			content: $("#content").val(),
		};
		
		$.ajax({
			//회원가입 수행 요청
			type:"POST",
			url:"/api/board",
			data: JSON.stringify(data), 
			contentType:"application/json; charset=utf-8",
			dataType: "json"
		}).done(function(resp){
			alert("글쓰기가 완료되었습니다.");
			location.href="/"
			
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		});
		},
		
		deleteByid:function(){	
		var id= $("#id").text();
		$.ajax({
			type:"DELETE",
			url:"/api/board/"+id,
			dataType: "json"
		}).done(function(resp){
			alert("삭제가 완료되었습니다.");
			location.href="/"
			
		}).fail(function(error){
			alert(JSON.stringify(error));
			
		});
	}
	

	}


index.init();