(function (window) {
	'use strict';

	// Your starting point. Enjoy the ride!


	var listHelper = '<li class="%completed%"><div class="view"><input class="toggle" type="checkbox" %checked%><label>%todo%</label><button class="destroy"></button></div><input class="edit" value="Create a TodoMVC template"></li>';
	var todoList = $('.todo-list');
	var selectResult = [];

	var remainItem = 0;

	//아이템 표시
	function showItemCount(number)
	{
		$('strong').text(number);
	}
  // 초기 완료하지 못한 아이템 수 가져오기
	function initItemNumber()
	{
		var number = 0;
		for(var i=0;i<selectResult.length;i++)
		{
			if(selectResult[i].completed==0)
			{
				number+=1;
			}
		}
		remainItem = number;
	}

//checkbox update function
	function toggleHandler()
	{
			var li = $(this).parent().parent();
			var index = li.index();
			var targetId = selectResult[index].id;

			if($(this).prop('checked'))
			{
				$.ajax({
	        url: 'http://localhost:8080/api/todos/'+targetId+'/1',
	        type: "PUT",
	        crossDomain: true,
	        success:function(result){
						li.attr('class','completed');
						showItemCount(remainItem-=1);
	        },
	        error:function(xhr,status,error){
	            console.log(error);
	        }
	    		});
			}
			else
			{


				$.ajax({
	        url: 'http://localhost:8080/api/todos/'+targetId+'/0',
	        type: "PUT",
	        crossDomain: true,
	        success:function(result){
						li.attr('class','view');
						showItemCount(remainItem+=1);
	        },
	        error:function(xhr,status,error){
	            console.log(error);
	        }
	    		});
			}
	}


	//clear function
	function clearHandler()
	{
		$.ajax({
			url: 'http://localhost:8080/api/todos/completed/1',
			type: "DELETE",
			crossDomain: true,
			success:function(result){
				$.each($('.completed'),function(index,value)
				{
					var liindex = $(this).index();
					console.log(liindex);
					selectResult.splice(liindex,1);
					$(this).remove();
				});

				showItemCount();
			},
			error:function(xhr,status,error){
					console.log(error);
			}
			});
	}

	//delete fuction

	function deleteHandler()
	{
			var li = $(this).parent().parent();
			var index = li.index();
			var targetId = selectResult[index].id;

			$.ajax({
				url: 'http://localhost:8080/api/todos/'+targetId,
				type: "DELETE",
				crossDomain: true,
				success:function(result){ // remove item
					li.remove();
					selectResult.splice(index,1);
					showItemCount(remainItem-=1);
				},
				error:function(xhr,status,error){
						console.log(error);
				}
				});
	}


	function selectByFilter(filter)
	{
		var selectUrl = 'http://localhost:8080/api/todos';

		if (filter ==='Active')
		{
			selectUrl+='/0';
		}
		else if(filter ==='Completed')
		{
			selectUrl+='/1';
		}

		$.ajax({
			method:'GET',
			url:selectUrl,
			dataType:'jsonp',
			success:function(response) //select 요청이 성공하면 화면에 뿌려준다.
			{
				selectResult = response;
				for(var i=0;i<selectResult.length;i++)
				{
					var completed = selectResult[i].completed;
					var list = listHelper.replace('%todo%',selectResult[i].todo);
					if(completed==0)
					{
						list = list.replace('%completed%','view');
						list = list.replace('%checked%','');
					}
					else if(completed==1)
					{
						list=list.replace('%completed%','completed');
						list = list.replace('%checked%','checked');
					}

					todoList.append(list);
				}
				$('.toggle').click(toggleHandler);
				$('.destroy').click(deleteHandler);
				$('.clear-completed').click(clearHandler);

				initItemNumber();
				showItemCount(remainItem);

			},
			error:function(xhr,status,error)
			{
				console.log(error);
			}

		});

	}

	// 초기 데이터를 불러오고 이벤트들을 셋함
	selectByFilter(null);



	// new -todo enter event
	$('.new-todo').keydown(function(key) {
		if(key.keyCode==13 && $(this).val()!='')
		{
			var list = listHelper.replace('%todo%',$(this).val());
			list = list.replace('%completed%','view');
			list = list.replace('%checked%','');


			var todoObject={};
			todoObject.todo=$(this).val();
			todoObject.completed=0;
			todoObject.date = new Date().getTime();

			$.ajax({
        url: 'http://localhost:8080/api/todos',
        type: "POST",
        crossDomain: true,
        data: JSON.stringify(todoObject),
				contentType:"application/json",
        dataType: "json",
        success:function(result){

					var ul = todoList.prepend(list);
					var newli = ul.children().first();
					newli.children('div').children('.toggle').click(toggleHandler);
					newli.children('div').children('.destroy').click(deleteHandler);
          todoObject.id=result;
					selectResult.unshift(todoObject);
					showItemCount(remainItem+=1);
        },
        error:function(xhr,status,error){
            console.log(error);
        }
    		});

			$(this).val('');
		}
		else if(key.keyCode==13 && $(this).val()==='')
		{
			alert('내용을 적어주세요!');
		}
	});

	/////////////////////////////////////////
	//////////////////////////////////////////
	/////////////////////////////////////////

	// filtering
 	$('a').click(function(evt)
 	{
		evt.preventDefault();
 		var selected = $(this).text();
 		$('a').removeAttr('class');
 		$(this).attr('class','selected');

 		if(selected === 'All')
 		{
 			todoList.children().remove();
			selectByFilter(null);
 		}
 		else if(selected === 'Active')
 		{
 			todoList.children().remove();
			selectByFilter('Active');

 		}
 		else if(selected === 'Completed')
 		{
 			todoList.children().remove();
			selectByFilter('Completed');
 		}

 	});

})(window);
