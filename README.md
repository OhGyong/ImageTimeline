# Image_Record_App
갤러리의 사진을 올리면 날짜별로 정렬하여 보여주는 앱.(몸무게 사진 찍어놓은 것을 날짜별로 기록하려고 시작)

https://user-images.githubusercontent.com/105263450/171398840-7fb105e5-db45-4820-be09-9e9f431c75d6.mp4

### 사용 기술
Grid RecyclerView, Room, contentResolver, ContextMenu, AAC(DataBinding, ViewModel, LiveData)

### 해야할 작업
1. ~~갤러리에서 뒤로 가기 시 앱 종료되는 것 수정.~~
2. ~~갤러리에서 사진 선택이나 뒤로 갔을 때 새로고침(사진 업데이트가 안되는 문제)~~
3. ~~같은 사진 중복 등록 수정.~~
4. ~~사진 삭제 기능 → 사진 선택도 가능해야 함.~~
5. ~~다크 모드 비활성화~~


### 일정
- 2022.12.29<br/>
  이미지 사이즈 조정<br/>
  viewModelScope로 변경<br/>
  RecyclerView의 notify 메서드 수정


- 2022.12.18~12.19<br/>
  리스트 갱신 코드 수정<br/>

- 2022.04.23<br/>
  다크 모드 비활성화<br/>
  앱 아이콘 변경<br/>
  패키지 명 변경 (example → android)<br/>
  스플래시 화면 추가
  
- 2022.04.22<br/>
  사진 삭제 기능 추가 → 리사이클러 뷰에 컨텍스트 메뉴 추가.<br/>
  
- 2022.04.21<br/>
  갤러리에서 뒤로 가기 시 앱 종료되는 것 수정 → `it.resultCode == RESULT_CANCELED` 사용<br/>
  갤러리에서 사진 선택이나 뒤로 갔을 때 새로고침 → `invalidateOptionsMenu()` 사용<br/>
  같은 사진 중복 등록 수정 → GridViewData에서 id를 삭제하고 uri를 PrimaryKey로 변경. `OnConflictStrategy.REPLACE` 사용하여 충돌 시 덮어쓰기 [참고](https://developer.android.com/reference/android/arch/persistence/room/OnConflictStrategy)

- 2022.04.20<br/>
  이미지 추가 버튼 수정<br/>
  RecyclerView 아이템 디자인 커스텀<br/>
  앱 바 가리기

- 2022.04.19<br/>
  Glide 의존성 추가<br/>
  ViewModel 작업

- 2022.04.15<br/>
  DataBinding, LiveData, ViewModel 의존성 추가<br/>
  GridView에 데이터 넣는 작업

- 2022.04.14<br/>
  Room, Coroutines 의존성 추가<br/>
  Room 적용

- 2022.04.13<br/>
  플로팅 버튼 추가<br/>
  gitignore 수정<br/>
  갤러리 사진 가져오기 <br/>
  contentResolver, cursor 사용 → 갤러리 이미지에서 날짜 정보 가져옴 

- 2022.04.12<br/>
  프로젝트 생성