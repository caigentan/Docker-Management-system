(function(b,a,c){b.fn.fullsearch=function(d){var e={search_data:"#search_data",search_title:"",search_content:"",list:".fullsearch-data",nodata:"",attribute:"text",highlight:false,};var d=b.extend(e,d);b(this).each(function(){var g=b(this);var s=b(e.search_data),t=s.find(e.search_title),c=s.find(e.search_content);d.list=b(this).data("list")||d.list;d.nodata=b(this).data("nodata")||d.nodata;d.attribute=b(this).data("attribute")||d.attribute;d.highlight=b(this).data("highlight")||d.highlight;var f=b(d.list);g.keyup(function(l){if(l.keyCode!=38&&l.keyCode!=40&&l.keyCode!=13){var j=g.val().toLowerCase();t.html('');c.html('');f.children().removeClass("selected").each(function(){var m=(d.attribute!="text")?b(this).attr(d.attribute).toLowerCase():b(this).text().toLowerCase();if(m.indexOf(j)==-1){g.trigger("_after_each")}else{s.show();var t_id=b(this)[0].id;t_id=t_id!=''?t_id:b(this).prev()[0].id;t_id=t_id!=''?t_id:b(this).prev().prev()[0].id;t_id=t_id!=''?t_id:b(this).prev().prev().prev()[0].id;t_id=t_id!=''?t_id:b(this).prev().prev().prev().prev()[0].id;var content='<li><a href="#'+t_id+'">'+b(this).text().substring(0,80)+"..."+'</li></a>';var tagName=$(this)[0].tagName;if(tagName=="H4"){!d.highlight?t.append(content):t.append(content).removeHighlight().highlight(j).show();}else{!d.highlight?c.append(content):c.append(content).removeHighlight().highlight(j).show();}
(j=='')?s.hide():1;}});$('a[href*=#]').anchor({transitionDuration:500});if(d.nodata){s.find(".no-results").remove();if(!t.html().length){s.find("h4").eq(0).hide();}else{s.find("h4").eq(0).show();}
if(!c.html().length){s.find("h4").eq(1).hide();}else{s.find("h4").eq(1).show();}
if(!t.html().length&&!c.html().length){s.children().first().removeAttr("id").clone().removeHighlight().addClass("no-results").show().prependTo(s).text(d.nodata)}}
g.trigger("_after")}})})};b(document).ready(function(){b('[data-toggle="fullsearch"]').fullsearch()})})(jQuery);jQuery.fn.highlight=function(t){function e(t,i){var n=0;if(3==t.nodeType){var a=t.data.toUpperCase().indexOf(i);if(a>=0){var s=document.createElement("mark");s.className="highlight";var r=t.splitText(a);r.splitText(i.length);var o=r.cloneNode(!0);s.appendChild(o),r.parentNode.replaceChild(s,r),n=1;}}else if(1==t.nodeType&&t.childNodes&&!/(script|style)/i.test(t.tagName)){for(var h=0;h<t.childNodes.length;++h){h+=e(t.childNodes[h],i);}}
return n}
return this.length&&t&&t.length?this.each(function(){e(this,t.toUpperCase())}):this},jQuery.fn.removeHighlight=function(){return this.find("mark.highlight").each(function(){with(this.parentNode.firstChild.nodeName,this.parentNode)replaceChild(this.firstChild,this),normalize()}).end()};