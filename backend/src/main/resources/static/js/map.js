var root = "";

//마커를 클릭했을 때 해당 장소의 상세정보를 보여줄 커스텀오버레이입니다
var placeOverlay = new kakao.maps.CustomOverlay({zIndex:1}), 
 contentNode = document.createElement('div'), // 커스텀 오버레이의 컨텐츠 엘리먼트 입니다 
 markers_apt= [], // 마커를 담을 배열입니다
 markers_category = [], // 마커를 담을 배열입니다
 currCategory = ''; // 현재 선택된 카테고리를 가지고 있을 변수입니다

var place_lat=0;
var place_lng=0;
var subway_name = "없음";
var subway_lat;
var subway_lng;
var store_name = "없음";
var store_lat;
var store_lng;

var mapContainer = document.getElementById("map"); // 지도를 표시할 div	
var mapOption = {
	    center: new kakao.maps.LatLng(37.566826, 126.9786567), // 지도의 중심좌표
	    level: 5, // 지도의 확대 레벨
	};

// 지도를 생성합니다
var map = new kakao.maps.Map(mapContainer, mapOption);

// 장소 검색 객체를 생성합니다
var ps = new kakao.maps.services.Places(map);

//지도에 idle 이벤트를 등록합니다
kakao.maps.event.addListener(map, 'idle', searchPlaces);

// 커스텀 오버레이의 컨텐츠 노드에 css class를 추가합니다 
contentNode.className = 'placeinfo_wrap';

// 커스텀 오버레이의 컨텐츠 노드에 mousedown, touchstart 이벤트가 발생했을때
// 지도 객체에 이벤트가 전달되지 않도록 이벤트 핸들러로 kakao.maps.event.preventMap 메소드를 등록합니다 
addEventHandle(contentNode, 'mousedown', kakao.maps.event.preventMap);
addEventHandle(contentNode, 'touchstart', kakao.maps.event.preventMap);

// 커스텀 오버레이 컨텐츠를 설정합니다
placeOverlay.setContent(contentNode);  

// 각 카테고리에 클릭 이벤트를 등록합니다
addCategoryClickEvent();

// 엘리먼트에 이벤트 핸들러를 등록하는 함수입니다
function addEventHandle(target, type, callback) {
    if (target.addEventListener) {
        target.addEventListener(type, callback);
    } else {
        target.attachEvent('on' + type, callback);
    }
}

// 카테고리 검색을 요청하는 함수입니다
function searchPlaces() {
    if (!currCategory) {
        return;
    }
    
    // 커스텀 오버레이를 숨깁니다 
    placeOverlay.setMap(null);

    // 지도에 표시되고 있는 마커를 제거합니다
    removeMarkers_category();
    console.log("search")
    ps.categorySearch(currCategory, placesSearchCB, {useMapBounds:true}); 
}
//장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB(data, status, pagination) {
	console.log("searchcb"+status);
	
    if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        displayPlaces(data);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {
    	if(currCategory=="SW8"){
    		subway_name = "없음";
    	}
    	if(currCategory=="CS2"){
    		store_name = "없음";
    	}
    } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        
    }
}

//장소검색이 완료됐을 때 호출되는 콜백함수 입니다
function placesSearchCB_ss(data, status, pagination) {
	console.log("searchcb"+status+" "+pagination	);
	
    if (status === kakao.maps.services.Status.OK) {
        // 정상적으로 검색이 완료됐으면 지도에 마커를 표출합니다
        displayPlaces_ss(data);
    } else if (status === kakao.maps.services.Status.ZERO_RESULT) {

    } else if (status === kakao.maps.services.Status.ERROR) {
        // 에러로 인해 검색결과가 나오지 않은 경우 해야할 처리가 있다면 이곳에 작성해 주세요
        
    }
}
function displayPlaces_ss(places) {
    // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
    // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
	var store_min = 1000;
	var submay_min = 1000;
    for ( var i=0; i<places.length; i++ ) {
            (function(place) {
                if(place.category_group_code=='SW8'){
                	var distance = calcDistance(place.y, place.x, place_lat, place_lng);
                	if(submay_min > distance){
                		subway_name = place.place_name +" "+distance+"m";
                		submay_min = distance;
                		document.getElementById("subway_name").innerHTML=subway_name;
                	}
                }
                else if(place.category_group_code=='CS2'){
                	var distance = calcDistance(place.y, place.x, place_lat, place_lng);
                	if(store_min > distance){
                		store_name = place.place_name +" "+distance+"m";
                		store_min = distance;
                		document.getElementById("store_name").innerHTML=store_name;
                	}
                }
            })(places[i]);
    }
}

// 검색 결과 목록이나 마커를 클릭했을 때 장소명을 표출할 인포윈도우를 생성합니다
var infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
// 커스텀 오버레이를 생성합니다
var customOverlay = new kakao.maps.CustomOverlay({
	xAnchor: 0.3,
	yAnchor: 0.91,
});
//주소-좌표 변환 객체를 생성합니다
var geocoder = new kakao.maps.services.Geocoder();

//지도에 카테고리 마커를 표출하는 함수입니다
function displayPlaces(places) {
	console.log(currCategory);
    // 몇번째 카테고리가 선택되어 있는지 얻어옵니다
    // 이 순서는 스프라이트 이미지에서의 위치를 계산하는데 사용됩니다
	var order = document.getElementById(currCategory).getAttribute('data-order');
    for ( var i=0; i<places.length; i++ ) {

            // 마커를 생성하고 지도에 표시합니다
            var marker = addMarker2(new kakao.maps.LatLng(places[i].y, places[i].x), order);

            // 마커와 검색결과 항목을 클릭 했을 때
            // 장소정보를 표출하도록 클릭 이벤트를 등록합니다
            (function(marker, place) {
                kakao.maps.event.addListener(marker, 'click', function() {
                    displayPlaceInfo(place);
                    
                });

            })(marker, places[i]);
    }
}

//검색 결과 목록과 마커를 표출하는 함수입니다
function displayMarkers(places) {
	placeOverlay.setMap(null);
    currCategory = '';
	changeCategoryClass();
	var fragment = document.createDocumentFragment();
    var bounds = new kakao.maps.LatLngBounds();
    var listStr = "";
    
	// 지도에 표시되고 있는 마커를 제거합니다
    removeMarkers_apt();
    removeMarkers_category();
	for(var i=0; i<places.length; i++) {
		var placePosition = new kakao.maps.LatLng(places[i].lat, places[i].lng);
		var marker = addMarker(placePosition, i);
		var itemEl = getListItem(i, places[i]); // 검색 결과 항목 Element를 생성합니다
		
		// 검색된 장소 위치를 기준으로 지도 범위를 재설정하기위해
		// LatLngBounds 객체에 좌표를 추가합니다
		bounds.extend(placePosition);
		// 마커와 검색결과 항목에 mouseover 했을때
		// 해당 장소에 인포윈도우에 장소명을 표시합니다
		// mouseout 했을 때는 인포윈도우를 닫습니다
		(function (marker, title, code, place) {
			kakao.maps.event.addListener(marker, "click", function () {
				displayInfowindow(marker, title, place);
				console.log(title + " " + code);
			});

		    kakao.maps.event.addListener(map, "click", function () {
		    	console.log(customOverlay);
		    	customOverlay.setMap(null);
		    });

		    itemEl.onmouseover = function () {
		    	displayInfowindow(marker, title);
		    };

		    itemEl.onmouseout = function () {
		    	customOverlay.setMap(null);
		    };
		    
		})(marker, places[i].aptName, places[i].aptCode, places[i]);

		fragment.appendChild(itemEl);
	}
	// 마커를 생성하고 지도에 표시합니다 

	// 검색된 장소 위치를 기준으로 지도 범위를 재설정합니다
	map.setBounds(bounds);
}

//마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker(position, idx, title) {
	var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/marker_number_blue.png", // 마커 이미지 url, 스프라이트 이미지를 씁니다
	imageSize = new kakao.maps.Size(36, 37), // 마커 이미지의 크기
	imgOptions = {
		spriteSize: new kakao.maps.Size(36, 691), // 스프라이트 이미지의 크기
		spriteOrigin: new kakao.maps.Point(0, idx * 46 + 10), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
		offset: new kakao.maps.Point(13, 37), // 마커 좌표에 일치시킬 이미지 내에서의 좌표
	},
	markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
	marker = new kakao.maps.Marker({
		position: position, // 마커의 위치
		image: markerImage,
	});

	marker.setMap(map); // 지도 위에 마커를 표출합니다
	markers_apt.push(marker); // 배열에 생성된 마커를 추가합니다

	return marker;
}

//카테고리 마커를 생성하고 지도 위에 마커를 표시하는 함수입니다
function addMarker2(position, order) {
    var imageSrc = 'https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/places_category.png', // 마커 이미지 url, 스프라이트 이미지를 씁니다
        imageSize = new kakao.maps.Size(27, 28),  // 마커 이미지의 크기
        imgOptions =  {
            spriteSize : new kakao.maps.Size(72, 208), // 스프라이트 이미지의 크기
            spriteOrigin : new kakao.maps.Point(46, (order*36)), // 스프라이트 이미지 중 사용할 영역의 좌상단 좌표
            offset: new kakao.maps.Point(11, 28) // 마커 좌표에 일치시킬 이미지 내에서의 좌표
        },
        markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize, imgOptions),
            marker = new kakao.maps.Marker({
            position: position, // 마커의 위치
            image: markerImage 
        });

    marker.setMap(map); // 지도 위에 마커를 표출합니다
    markers_category.push(marker);  // 배열에 생성된 마커를 추가합니다

    return marker;
}
// 지도 위에 표시되고 있는 마커를 모두 제거합니다
function removeMarkers_apt() {
	for (var i = 0; i < markers_apt.length; i++) {
		markers_apt[i].setMap(null);
	}
	markers_apt = [];
}
function removeMarkers_category() {
	for (var i = 0; i < markers_category.length; i++) {
		markers_category[i].setMap(null);
	}
	markers_category = [];
}
//클릭한 마커에 대한 장소 상세정보를 커스텀 오버레이로 표시하는 함수입니다
function displayPlaceInfo (place) {
    var content = '<div class="placeinfo">' +
                    '   <a class="title" href="' + place.place_url + '" target="_blank" title="' + place.place_name + '">' + place.place_name + '</a>';   

    if (place.road_address_name) {
        content += '    <span title="' + place.road_address_name + '">' + place.road_address_name + '</span>' +
                    '  <span class="jibun" title="' + place.address_name + '">(지번 : ' + place.address_name + ')</span>';
    }  else {
        content += '    <span title="' + place.address_name + '">' + place.address_name + '</span>';
    }                
   
    content += '    <span class="tel">' + place.phone + '</span>' + 
                '</div>' + 
                '<div class="after"></div>';

    contentNode.innerHTML = content;
    placeOverlay.setPosition(new kakao.maps.LatLng(place.y, place.x));
    placeOverlay.setMap(map);  
}

//각 카테고리에 클릭 이벤트를 등록합니다
function addCategoryClickEvent() {
    var category = document.getElementById('category'),
        children = category.children;

    for (var i=0; i<children.length; i++) {
        children[i].onclick = onClickCategory;
    }
}
//카테고리를 클릭했을 때 호출되는 함수입니다
function onClickCategory() {
	console.log(this);
    var id = this.id,
        className = this.className;

    placeOverlay.setMap(null);

    if (className === 'on') {
        currCategory = '';
        changeCategoryClass();
        removeMarkers_category();
    } else {
        currCategory = id;
        changeCategoryClass(this);
        searchPlaces();
    }
}

// 클릭된 카테고리에만 클릭된 스타일을 적용하는 함수입니다
function changeCategoryClass(el) {
    var category = document.getElementById('category'),
        children = category.children,
        i;

    for ( i=0; i<children.length; i++ ) {
        children[i].className = '';
    }

    if (el) {
        el.className = 'on';
    } 
} 
//검색결과 항목을 Element로 반환하는 함수입니다
function getListItem(index, place) {
	var el = document.createElement("li");
	var itemStr = `
		<span class="markerbg marker_${index + 1}></span>
		<div class="info"><h5>${place.aptName}</h5> <button>관심등록</button>
		<span>${place.sidoName} ${place.gugunName} ${place.dongName} ${place.jibun}</span>
	`;
	el.innerHTML = itemStr;
	el.className = "item";

	return el;
}

//검색결과 목록 또는 마커를 클릭했을 때 호출되는 함수입니다
//인포윈도우에 장소명을 표시합니다
function displayInfowindow(marker, title, place) {
	place_lat = place.lat;
	place_lng = place.lng;
	
	currCategory = 'SW8';
	ps.categorySearch(currCategory, placesSearchCB_ss, {location:marker.getPosition(),radius :1000}); 
	currCategory = 'CS2';
	ps.categorySearch(currCategory, placesSearchCB_ss, {location:marker.getPosition(),radius :1000}); 
	currCategory = '';
	console.log(title);
	var content = `
		<div class="overlaybox">
			<div class="boxtitle">${title}</div>
			<div class="first"><img src="${root}/img/apt.png" style="width:247px; height:136px;" alt=""></div>
			<ul>
				<li class="up">
					<span class="title">건축년도</span>
					<span class="count">${place.buildYear}</span>
				</li>
				<li>
					<span class="title">주소</span>
					<span class="count">${place.sidoName} ${place.gugunName} ${place.dongName} ${place.jibun}</span>
				</li>
				<li>
					<span class="title">최신거래금액</span>
					<span class="count">${place.recentPrice}</span>
				</li>
				<li>
					<span class="last" id="recenthistor" data-toggle="modal" data-target="#myModal">아파트정보 update</span>
				</li>
				<li>
					<span class="title">지하철역</span>
					<span class="count" id="subway_name">${subway_name}</span>
				</li>
				<li>
					<span class="title">편의점</span>
					<span class="count" id="store_name">${store_name}</span>
				</li>
			</ul>
		</div>
	`;
	var position = new kakao.maps.LatLng(marker.getPosition().getLat()+0.00033, marker.getPosition().getLng()-0.00003);
	customOverlay.setPosition(position);
	customOverlay.setContent(content);

	
	customOverlay.setMap(map);
}

//검색결과 목록의 자식 Element를 제거하는 함수입니다
function removeAllChildNods(el) {
	while (el.hasChildNodes()) {
		el.removeChild(el.lastChild);
	}
}

// 좌표 거리계산
//function calcDistance(lat1,lng1,lat2,lng2) {
//    var ret = 0;
//    var latA = 111;
//    var lngB = 88.8;
//    ret = Math.sqrt(
//            Math.pow((Math.abs(lat1 - lat2) * latA),2)
//            +
//            Math.pow((Math.abs(lng1 - lng2) * lngB),2)
//          ) *1000;//m
//    return ret.toFixed(2);
//  }

function calcDistance(lat1, lon1, lat2, lon2)
{
  var theta = lon1 - lon2;
  dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(lat1))
        * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
  dist = Math.acos(dist);
  dist = rad2deg(dist);
  dist = dist * 60 * 1.1515;
  dist = dist * 1.609344;
  return Number(dist*1000).toFixed(2);
}

function deg2rad(deg) {
  return (deg * Math.PI / 180);
}
function rad2deg(rad) {
  return (rad * 180 / Math.PI);
}