# 메이븐과 스프링 STS 사용법
지금까지는 웹 애플리케이션을 구현할 때 이클립스에서 웹 프로젝트를 직접 생성한 후 스프링 기능에 필요한 라이브러리를 직접 다운로드하여 사용했다.<br>
스프링이 나온 초기에는 이런 방식으로 프로그램을 개발했다. 하지만 스프링 버전이 자주 업데이트됨에 따라 불펴함이 따랐다.<br>
다시 말해 업데이트할 때마다 관련 기능의 라이브러리를 일일이 수정해야 했고, 라이브러리의 기능 사용법이 달라지면 소스도 같이 수정해 주어야 해서 불편했다.<br>
그래서 현재는 메이븐과 같은 도구를 이용해 자동으로 스프링의 라이브러리 기능을 관리함녀서 프로그램을 개발한다. <br>

## 메이븐 
메이븐은 프로젝트 구조와 내용을 기술하는 선언적 접근 방식의 오픈 소스 빌드 툴이다. <br>
메이븐을 사용하면 프로젝트 종속 라이브러리들과 그 라이브러리에 의존하는 Dependency 자원까지 관리할 수 있다.<br>
메이븐은 프로젝트 전반의 리소스 관리와 설정 파일 그리고 이와 관련된 표둔 디렉터리 구조를 처음부터 일관된 형태로 구성하여 관리한다.<br>
메이븐을 사용하면 컴파일과 동시에 빌드를 수행할 수 있을 뿐만 아니라 관련된 라이브러리도 일관성 있게 관리할 수 있어 편리하다.<br>
지금까지 스프링 실습에서는 라이브러리 관련 jar 파일을 내려 받아 프로젝트에 추가할 경우 이와 연관된 종속 라이브러리까지 다 찾아서 추가해 주어야 했다.<br>
그러나 메이븐을 사용하면 이런 의존 관걔를 자동으로 관리할 수 있다.<br>

## log4j
지금까지의 실습이 정상적으로 실행되었는지 확인하기 위해 println()메소드를 이용해 데이터를 콘솔로 출력해서 살펴 본다. <br>
개발이 끝나고 실제 서비스를 한 후로는 더 이상 메시지를 출력하느 구믄은 필요가 없어진다. 따라서 주석 처리를 하거나 삭제를 해야한다.<br>
하지만 유지관리를 하다 보면 필요한 경우 다시 콘솔에 메시지를 출력해야하는 경우가 발생하기도 한다. <br>
실제 애플리케이션에서는 유지관리를 위해 웹 사이트에 접속한 사용자 정보나 각 클랫의 메소드 호출 시각 등 여러 가지 정보를 파일로 저장해서 관리한다.<br>
이런 로그 관련 기능을 제공하느 것이 log4j이다. <br>

## tiles
일반적인 웹 애플리케이션 화면 구조는 상단 부분이난 왼쪽 메뉴 그리고 하단 부분을 담당하는 페이지를 따로 만들어 놓고 브라우저에서 웹 페이지를 요청하면 
본문 화면만 추가하여 보여준다. 이러한 화면 레이아웃 기능을 제공하는 것이 바로 타일즈 기능이다.<br>
타일즈는 화면의 레이아웃을 쉽게 구현하기 위해 도입된, JSP 페이지 레이아웃을 위한 프레임워크이다.<br>
스프링이나 스트러츠에서도 제공하지만 독립적으로 타일즈 기능을 사용할 수도 있다.<br>
타일즈를 사용하던 다음과 같은 장점이 있다.<br>
  - 페이지 레이아웃을 쉽고 단순하게 구현할 수 있다.
  - 공통된 레이아웃을 사용하므로 유지관리가 쉽다.