var myId;
var lnameHash={};
var picHash = {};
var fidArray=new Array();
var nameHash={};
var workingList;
var path2;
var listID=0;
var CLICK_COUNT=0;

function login() {
    FB.login(function(response) {
        if (response.authResponse) {
            myId = response.authResponse.userID;
            var accessToken = response.authResponse.accessToken;
            LoadPage();
        }
    },{scope: 'manage_friendlists,read_friendlists,offline_access'});
}

function FBInit(){
    FB.getLoginStatus(function(response){
          if (response.status === 'connected'){
            myId = response.authResponse.userID;
            path2="/afs/cs.unc.edu/home/bartel/fbfriendslist/data/appdata/step1/"+myId+"_creator.txt";
            jQuery.ajax({
                url:    'PHPScripts/writeTxtFile.php?path='+path2+"&text="+currentDateAndTime()+" Login Creator",
                success: function(data) {
                },
                async: false
            });
            var accessToken = response.authResponse.accessToken;
            LoadPage();
          }else{         
            // not_logged_in or not_authorized
            login();
          }
    });
}

var loadFriendPool = function(){
    FB.api("/me/friends?fields=name,picture,last_name",function(response){
        if(!response.error){
            var friends = response.data;
            for (var i=0; i < friends.length; i++) {
                fidArray[i]=friends[i].id;
                picHash[fidArray[i]]=friends[i].picture.data.url;
                nameHash[fidArray[i]]=friends[i].name;
                lnameHash[fidArray[i]]=friends[i].last_name;
            }
            //Friend.generate(file, picHash); 
            Friend.generate(fidArray,nameHash,picHash,lnameHash);     
            //loadLists();
        }
    });
};

function LoadPage(){
    loadFriendPool();
}

$(document).ready(function()
{   
    $("#instruction2").hide();
    $("#instruction3").hide();
    $("#buttonGroup2").hide();
    $("#next1").click(function(){
        jQuery.ajax({
            url:    'PHPScripts/writeTxtFile.php?path='+path2+"&text="+currentDateAndTime()+" Click "+CLICK_COUNT,
            success: function(data) {
            },
            async: false
        });
        jQuery.ajax({
            url:    'PHPScripts/writeTxtFile.php?path='+path2+"&text="+currentDateAndTime()+" Leave Creator",
            success: function(data) {
                document.location.href='editor.html';
            },
            async: false
        });
    });
    $("body").on("change","input.checkbox1",null,function(e){
        CLICK_COUNT++;
    });

    $("#preSelect-all").click(function(){
        $("#tablebody .checkbox1").prop('checked', true);
        recordClick("Select","All", "FriendPool" );
    });
    $("#listSelect-all").click(function(){
        $("#UL div:visible input").prop('checked', true);
        recordClick("Select","All", workingList);
    });
    $("#preSelect-none").click(function(){
        $("#tablebody .checkbox1").prop('checked', false);
         recordClick("Unselect","All", "FriendPool" );
    });
    $("#listSelect-none").click(function(){
        $("#UL div:visible input").prop('checked', false);
        recordClick("Unselect","All", workingList);
    });

    $(".deleteButton").click(function(){
        var memberList="";
        if($('#UL div:visible input:checkbox:checked').length==0){
            alert("No friends selected");
        }
        $('#UL div:visible input:checkbox:checked').parents("tr").each(function(){
            //console.log($(this).html()+"delete from "+$(this).parent().parent().siblings("span").text());
            this.remove();
            memberList=memberList+$(this).attr("data-id")+", ";

        });
        if(memberList.length>0){
            memberList=memberList.substring(0,memberList.length-2);
            CLICK_COUNT++;
            recordClick("Delete",memberList, workingList );
        }
    });
    $("#ULaddButton").click(function(){
        var memberList="";
        if($('#tablebody input:checkbox:checked').length==0){
            alert("No friends selected");
        }else{
            if($("#UL div:visible tbody").length==0){
                alert("Please create or select a working list first!");
            }else{
                $('#tablebody input:checkbox:checked').each(function(){
                    var b=$(this).parents("tr").attr("data-id");
                    var a=$("<tr class=\"row\" data-id="+b+"></tr>").append($(this).parents("tr").html());
                    var test=true;
                    $("#UL div:visible tbody").children("tr").each(function(){
                        if($(this).attr("data-id")==b){
                             test=false;
                        }
                    });
                    if(test){
                        $("#UL div:visible tbody").append(a);
                        memberList=memberList+b+", ";
                    }
                });
                if(memberList.length>0){
                    memberList=memberList.substring(0,memberList.length-2);
                    CLICK_COUNT++;
                    recordClick("Add", memberList,workingList);
                }
                $('#tablebody input:checkbox:checked').prop('checked', false);
            }
        }
    });

    $("#UL").on('click','button.rename',null, function(e){
        $(this).parent().hide();
        $(this).parent().siblings("div").show();
    });

    $("#UL").on('click','button.savename',null, function(e){
        $(this).parent().hide();
        $(this).parent().siblings("div").show();
        var cl = $(this).parent().parent().attr("class");
        var oldname=workingList;
        workingList=$(this).siblings("input").val();
        //$(this).siblings("input").val(workingList);
        $(this).parent().siblings("div.namedis").children("span").text("List Name: "+workingList);
        $("#UL div."+cl).attr("data-name",workingList);
        $("#UR div."+cl).children("span").text(workingList);
        $("#UR div."+cl).attr("data-name",workingList);
        recordClick("Rename", workingList+" to ", oldname);
    });

    $("#UL").on('click','button.cancelname',null, function(e){
        $(this).parent().hide();
        $(this).parent().siblings("div").show();
        $(this).siblings("input").val(workingList);
    });

    $("#UR").on('click','button.editB',null,function(e){
        $("#UL div:visible").hide();
        $("#UL .editbuttons").show();
        workingList=$(this).siblings("span").text();
        var listClass=$(this).parent().attr("class");
        $("#UL div."+listClass).show();
        $("#UL div."+listClass+" div.namedis").show();
        $("#UL div."+listClass+" div.table-div1").show();
        CLICK_COUNT++;
    });

    $("#UR").on('click','button.delB',null,function(e){
        var name=$(this).siblings("span").text();
        var listClass=$(this).parent().attr("class");
        var id="div."+listClass;
        $("#UL "+id).remove();
        $(this).parent().remove();
        CLICK_COUNT++;
        recordClick("Discard"," ", name);
    });

    $("#createnew").on("click", function(){
        if($("#listnameinput").val()==""){
            alert("Please enter a valid list name");
        }
        else{
            if(!checkName($("#listnameinput").val())){
                $("#listnameinput").val("");
                alert("Repeated name. Please enter another name.");
            }else{
                $("#UL div:visible").hide();
                $("#UL .editbuttons").show();
                var name=$("#listnameinput").val();
                workingList=name;
                $("#namediv").append($("<div data-name=\""+name+"\" class=\"list"+listID+"\"><button type=\"button\" class=\"editB\">Edit</button><button type=\"button\" class=\"delB\">Delete List</button><span>"+name+"</span></div>"));
                $("#UL").append(buildFrindListUL(name));
                $("#listnameinput").val("");
                recordClick("Create", " ",workingList);
                listID++;
            }
        }
    });

    // $('#save-to-local').click(function(event){
    //     event.preventDefault();
    //     //export to facebook
    //     var memberlist='';
    //     $('#UL div:visible tr').each(function(){
    //         memberlist=memberlist+$(this).attr('data-id');
    //         memberlist=memberlist+',';
    //     });
    //     memberlist=memberlist.substring(0,memberlist.length-1);
    //     recordClick("Finish", memberlist,workingList);
    //     alert("List successfully saved in server!");
    //     var select="div."+workingList;
    //     $(select).hide();
        
    // });

    $('#export-to-FB').click(function(event){
        event.preventDefault();
        //export to facebook
        var memberlist='';
        var friendlistId;
        
        $('#UL div:visible tr').each(function(){
            memberlist=memberlist+$(this).attr('data-id');
            memberlist=memberlist+',';
        });
        memberlist=memberlist.substring(0,memberlist.length-1);
        
        FB.api('/me/friendlists', 'post', {name:workingList},
        function(response) {
            if (!response||response.error){
                console.log('Creating FL error: '+response.error.message);
                alert('Export failed: '+response.error.message);
            } else {
                console.log('Creating FL success, id: '+response.id);
                friendlistId=response.id;
                FB.api('/'+friendlistId+'/members','post',{members:memberlist},
                function(memberResponse){
                    if (!memberResponse||memberResponse.error) {
                        console.log('Adding members failed: '+memberResponse.error.message);
                        alert('Export failed: '+memberResponse.error.message);
                    } else {
                        console.log('Adding members success: '+memberResponse);
                        alert('Export succeeded!');
                    }
                });
            }
        }); 
        var select="div."+workingList;
        $(select).hide();
        recordClick("Export", memberlist,workingList);
    });
}); 

var buildFrindListUL = function(name)
{   
    //container div
    var div0=$("<div data-name=\""+name+"\" class=\"list"+listID+"\"></div>");
    //display name div
    var div=$("<div class=\"namedis\"><span class=\"namespan\">List Name: "+name+"</span><button type=\"button\" class=\"rename\">Rename</button></div>");
    //edit name div
    var div2=$("<div><span class=\"namespan\">List Name: </span><input type=\"text\" class=\"nameinput\" value=\""+name+"\"><button type=\"button\" class=\"savename\">Save</button><button type=\"button\" class=\"cancelname\">Cancel</button>");   
    //member table
    var table=$("<div class=\"table-div1\"><table class=\"table2\"><tbody></<tbody></table></div>");
    return div0.append(div).append(div2.hide()).append(table);
}

function currentDateAndTime(){
    var d = new Date();
    var month = d.getMonth()+1;
    var time = d.getFullYear()+"-"+month+"-"+d.getDate()+" "+d.getHours()+":"+d.getMinutes()+":"+d.getSeconds();
    return time;
}

function checkName(name){
    var test=true;
    $("#UR div.URlist").each(function(){
        if($(this).children("span").text()==name){
            test=false;
        }
    });
    return test;
}

var loadLists=function(){
    $("#UL .editbuttons").show();
    var path=findMyFolder();
    var file;
    jQuery.ajax({
        url:    'PHPScripts/readTxtFile.php?path='+path+"&file=output.txt",
        success: function(data) {
            file = data;
        },
        async: false
    });  
    var lines = file.split("\r\n");
    index=0;
    var line2, number, name, row,id;
    var div0,div,div2,table,tbody,row;
    var count=1;
    while(index<lines.length-2){
        //name="Clique"+count;
        name="";
        index++;
        line2=lines[index].split(" ");
        number=1*line2[2];
        tbody=$("<tbody></<tbody>");
        for(var i=0;i<number;i++){
            id=lines[index+i+1];
            if(id!=myId){
                if(i==0){
                    name=name+nameHash[id].split(" ")[0]+"_to_";
                }
                if(i==number-1){
                    name=name+nameHash[id].split(" ")[0];
                }
                row=$("<tr class=\"row\" data-id=\""+id+"\"><td class=\"checktd\"><input class=\"checkbox1\" type=\"checkbox\"></input></td><td><img class=\"profile\" alt=\""+id+"\" src=\""+picHash[id]+"\"></td><td>"+nameHash[id]+"</td></tr>");
                tbody.append(row);
            }   
        }
        div0=$("<div data-name=\""+name+"\" class=\"list"+listID+"\"></div>");
       
        //display name div
        div=$("<div class=\"namedis\"><span class=\"namespan\">List Name: "+name+"</span><button type=\"button\" class=\"rename\">Rename</button></div>");
        //edit name div
        div2=$("<div><span class=\"namespan\">List Name: </span><input type=\"text\" class=\"nameinput\" value=\""+name+"\"><button type=\"button\" class=\"savename\">Save</button><button type=\"button\" class=\"cancelname\">Cancel</button>");   
        //member table
        table=$("<div class=\"table-div1\"><table class=\"table2\"></table></div>");
        
        $("#namediv").append($("<div data-name=\""+name+"\" class=\"list"+listID+"\"><button type=\"button\" class=\"editB\">Edit</button><button type=\"button\" class=\"delB\">Delete List</button><span>"+name+"</span></div>"));
        listID++;
        div0=div0.append(div).append(div2.hide()).append(table.append(tbody));
        $("#UL").append(div0.hide());
        index=index+number+2;
    }
}

var findMyFolder = function(){
    var path;
    jQuery.ajax({
        url:    'PHPScripts/findMyDataFolder.php?id='+myId,
        success: function(data) {
            path = data;
            },
        async: false
   }); 
    
   if(path=="No Folder Found")
   {
       alert("No friendship data found from Facebook. Please go to the data collection app @ http://apps.facebook.com/275299722579344/");
   }
   else if(path=="Folder Not Complete")
   {
       alert("Data collection not complete yet. Please wait for a couple hours and try again.");
   }
   return path;
}

function recordClick(action, memberList, workingList){
    var log=currentDateAndTime()+" "+action+" "+workingList+" "+memberList;
    jQuery.ajax({
            url:    'PHPScripts/writeTxtFile.php?path='+path2+"&text="+log,
            success: function(data) {
                },
            async: false
    });
    // jQuery.ajax({
    //         url:    'PHPScripts/writeTxtFile.php?path='+path2+"&text="+currentDateAndTime()+" List Name: "+workingList,
    //         success: function(data) {
    //             },
    //         async: false
    // });

    //  jQuery.ajax({
    //         url:    'PHPScripts/writeTxtFile.php?path='+path2+"&text="+action+" "+memberList,
    //         success: function(data) {
    //             },
    //         async: false
    // });
}