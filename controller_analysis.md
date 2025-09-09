# Pamily 프로젝트 분석

이 문서는 Pamily Spring Boot 프로젝트의 전체 아키텍처, 계층별 역할 및 주요 로직을 분석합니다. 여기서 'shop'은 'board'를 의미합니다.

## 프로젝트 아키텍처 요약

이 프로젝트는 웹 요청을 처리하는 `Controller`, 비즈니스 로직을 담당하는 `Service`, 데이터베이스와 상호작용하는 `Repository`로 구성된 전형적인 계층형 아키텍처를 따릅니다. 데이터 전송을 위해 `DTO`를 사용하며, 데이터베이스 테이블은 `JPA Entity`로 매핑됩니다.

인증 및 인가는 Spring Security를 통해 구현되며, 다음 세 가지 방식을 모두 지원하는 복합적인 구조를 가집니다:
1.  **Form 기반 로그인:** 전통적인 세션 방식의 로그인입니다.
2.  **OAuth2 소셜 로그인:** Google을 이용한 간편 로그인입니다.
3.  **JWT 기반 API 인증:** 외부 클라이언트와의 통신을 위한 토큰 기반 인증입니다.

---

## `Controller` 분석

### `BestController.java`

- **기본 경로:** `/sample/`
- **목적:** "베스트" 또는 추천 콘텐츠 페이지를 처리합니다.

| 메서드 | 경로 | HTTP Verb | 권한 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `exbest` | `/best` | `GET` | `USER` | 베스트 콘텐츠 페이지에 표시할 게시물 및 프로필의 페이지네이션된 목록을 가져옵니다. |

### `JoinController.java`

- **기본 경로:** `/join/`
- **목적:** 사용자 등록을 관리합니다.

| 메서드 | 경로 | HTTP Verb | 권한 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `exsignUp` | `/signUp` | `GET` | `permitAll` | 사용자 회원가입 양식을 표시합니다. |
| `formSignUp` | `/signUp` | `POST` | `permitAll` | 회원가입 양식을 처리합니다. 신규 회원을 등록하고 성공 또는 실패 메시지를 알림으로 표시합니다. |

### `MainController.java`

- **기본 경로:** `/sample/`
- **목적:** 메인 피드/대시보드를 처리합니다.

| 메서드 | 경로 | HTTP Verb | 권한 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `mainList` | `/main` | `GET` | `permitAll` | 메인 애플리케이션 뷰를 위해 페이지네이션된 게시물 목록과 사용자의 친구 목록을 가져옵니다. |

### `MemberController.java`

- **기본 경로:** `/member/`
- **목적:** 사용자 계정, 로그인, 프로필 정보를 관리합니다.

| 메서드 | 경로 | HTTP Verb | 권한 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `login` | `/login` | `GET` | `permitAll` | 로그인 페이지를 표시하고 오류/로그아웃 메시지를 처리합니다. |
| `findPass` | `/findPass` | `GET` | `permitAll` | 잊어버린 비밀번호를 찾기 위한 양식을 표시합니다. |
| `findPass` | `/findPass` | `POST` | `permitAll` | 비밀번호 찾기 요청을 처리합니다. 이메일과 휴대폰 번호를 확인한 후 비밀번호 재설정 페이지로 리디렉션합니다. |
| `newPass` | `/newPass` | `GET` | `permitAll` | 새 비밀번호를 설정하기 위한 양식을 표시합니다. |
| `newPass` | `/newPass` | `POST` | `permitAll` | 새 비밀번호 제출을 처리하고 회원의 자격 증명을 업데이트합니다. |
| `updateMInfo` | `/updateMInfo` | `GET` | `USER` | 사용자가 자신의 계정 정보를 업데이트하기 위한 양식을 표시합니다. |
| `gogo` | `/update` | `GET` | `USER` | 사용자가 소셜 미디어를 통해 가입했는지 확인합니다. 그렇다면 편집을 방지하고, 그렇지 않으면 업데이트 페이지로 리디렉션합니다. |
| `updateMInfo` | `/updateMInfo` | `POST` | `USER` | 회원 정보 업데이트 양식 제출을 처리합니다. |

### `MyProfileController.java`

- **기본 경로:** `/sample/`
- **목적:** 사용자 자신의 프로필 페이지를 관리합니다.

| 메서드 | 경로 | HTTP Verb | 권한 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `exProfile` | `/myProfile` | `GET` | `permitAll` | 개인 프로필 페이지에 표시할 사용자의 게시물과 프로필 이미지를 가져옵니다. |
| `register` | `/profileModify` | `GET` | `permitAll` | 프로필 수정 페이지를 표시합니다. |
| `register` | `/profileModify` | `POST` | `permitAll` | 프로필 수정 양식 제출을 처리합니다. |

### `PostController.java`

- **기본 경로:** `/sample/`
- **목적:** 새 게시물 생성을 처리합니다.

| 메서드 | 경로 | HTTP Verb | 권한 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `register` | `/uploadPage` | `GET` | `permitAll` | 새 게시물을 생성/업로드하기 위한 페이지를 표시합니다. |
| `register` | `/uploadPage` | `POST` | `permitAll` | 새 게시물 제출을 처리하고 메인 피드로 리디렉션합니다. |

### `ProfileController.java`

- **기본 경로:** `/sample/`
- **목적:** 프로필 관련 작업을 위한 것이지만 현재 활성 요청 매핑이 없습니다.

### `RootController.java`

- **기본 경로:** `/`
- **목적:** 애플리케이션의 진입점 역할을 합니다.

| 메서드 | 경로 | HTTP Verb | 권한 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `exmainPage` | `/` 또는 "" | `GET` | `permitAll` | 인증된 사용자를 메인 피드(`/sample/main`)로, 그 외 사용자는 로그인 페이지(`/member/login`)로 리디렉션합니다. |

### `ShopController.java`

- **기본 경로:** `/shop/`
- **목적:** 전자상거래/상점 섹션을 관리합니다.

| 메서드 | 경로 | HTTP Verb | 권한 | 설명 |
| :--- | :--- | :--- | :--- | :--- |
| `shopRead`, `modify` | `/read`, `/modify` | `GET` | `USER` (인증 필요) | 상점 아이템의 상세 정보를 보거나 수정하기 위해 표시합니다. (`@AuthenticationPrincipal` 사용) |
| `shopModify` | `/modify` | `POST` | `USER` (인증 필요) | 상점 아이템 수정을 처리합니다. (`@AuthenticationPrincipal` 사용) |
| `remove` | `/remove` | `POST` | `USER` (인증 필요) | 상점 아이템과 관련된 이미지 및 댓글을 삭제합니다. |
| `exshop` | `/shop` | `GET` | `USER` | 상점의 모든 아이템 목록을 표시합니다. |
| `exshopReg` | `/shopreg` | `GET` | `USER` | 상점에 새 아이템을 등록하기 위한 양식을 표시합니다. |
| `exshopRegPost` | `/shopreg` | `POST` | `USER` | 새 상점 아이템 제출을 처리합니다. |

### `ShopReplyController.java` (`@RestController`)

- **기본 경로:** `/shop/reviews`
- **목적:** 상점 아이템에 대한 댓글/리뷰 관리를 위한 REST API를 제공합니다.

| 메서드 | 경로 | HTTP Verb | 설명 |
| :--- | :--- | :--- | :--- |
| `getShopReplyList` | `/{sid}/all` | `GET` | 특정 상점 아이템(`sid`)에 대한 모든 댓글 목록을 검색합니다. |
| `addShopReply` | `/{sid}` | `POST` | 상점 아이템에 새 댓글을 추가합니다. 요청 본문에 댓글 데이터를 포함해야 합니다. |
| `modifyShopReply` | `/{sid}/{srid}` | `PUT` | 기존 댓글(`srid`)을 수정합니다. |
| `removeShopReply` | `/{sid}/{srid}` | `DELETE` | 특정 댓글(`srid`)을 삭제합니다. |

### `ShopUploadController.java` (`@RestController`)

- **기본 경로:** (클래스 수준 없음)
- **목적:** 상점 아이템 전용 파일 업로드를 처리하기 위한 REST API를 제공합니다.

| 메서드 | 경로 | HTTP Verb | 설명 |
| :--- | :--- | :--- | :--- |
| `shopUploadFile` | `/shopUploadAjax` | `POST` | 상점 아이템에 대한 AJAX 다중 파일 업로드를 처리합니다. 업로드된 이미지의 썸네일을 생성합니다. |
| `getFile` | `/shopDisplay` | `GET` | 브라우저에 표시하기 위해 업로드된 상점 아이템 이미지를 검색하여 제공합니다. |
| `removeFiles` | `/shopRemoveFile` | `POST` | 지정된 상점 아이템 파일과 해당 썸네일을 삭제합니다. |

### `UploadImageController.java` (`@RestController`)

- **기본 경로:** (클래스 수준 없음)
- **목적:** 일반적인 이미지 업로드(예: 게시물용)를 처리하기 위한 REST API를 제공합니다.

| 메서드 | 경로 | HTTP Verb | 설명 |
| :--- | :--- | :--- | :--- |
| `uploadFile` | `/uploadAjax` | `POST` | AJAX 다중 파일 업로드를 처리합니다. 파일이 이미지인지 확인하고 썸네일을 생성합니다. |
| `getFile` | `/display` | `GET` | 표시를 위해 업로드된 이미지를 검색하여 제공합니다. 원본 또는 썸네일 버전을 제공할 수 있습니다. |
| `removeFiles` | `/removeFile` | `POST` | 지정된 파일과 해당 썸네일을 삭제합니다. |

---

# DTO (Data Transfer Object) 분석

이 섹션은 프로젝트에서 계층 간 데이터 전송을 위해 사용되는 DTO 객체들을 설명합니다.

### `MemberDTO.java`
- **목적:** 회원 정보를 전송합니다.
- **주요 필드:** `mid`, `email`, `password`, `name`, `mobile`, `fromSocial`

### `PageRequestDTO.java`
- **목적:** 클라이언트로부터 페이지네이션 요청 파라미터를 받습니다.
- **주요 필드:** `page`, `size`, `type`, `keyword`, `scno`
- **주요 메서드:** `getPageable(Sort sort)`는 Spring Data의 `Pageable` 객체를 생성하여 반환합니다.

### `PageResultDTO.java`
- **목적:** 페이지네이션된 쿼리 결과를 클라이언트에 전달하기 위해 감싸는 역할을 합니다.
- **주요 필드:** `dtoList`, `totalPage`, `page`, `size`, `start`, `end`, `prev`, `next`, `pageList`

### `PostDTO.java`
- **목적:** 게시물 데이터를 전송합니다.
- **주요 필드:** `pid`, `content`, `mid`, `imageDTOList`, `replyCnt`

### `ProfileDTO.java`
- **목적:** 사용자 프로필 정보를 전송합니다.
- **주요 필드:** `profileId`, `mid`, `profileImageDTOList`

### `RelationDTO.java`
- **목적:** 회원 간의 팔로우/팔로워 관계를 전송합니다.
- **주요 필드:** `rid`, `following`, `follower`

### `ReplyDTO.java`
- **목적:** 게시물 댓글 정보를 전송합니다.
- **주요 필드:** `rid`, `pid`, `mid`, `name`, `email`, `text`

### `ShopCateDTO.java`
- **목적:** 상점 상품 카테고리 정보를 전송합니다.
- **주요 필드:** `scno`, `cateName`

### `ShopDTO.java`
- **목적:** 상점 상품 정보를 전송합니다.
- **주요 필드:** `sid`, `title`, `content`, `mid`, `name`, `scno`, `cateName`, `shopImageDTOList`, `shopReplyCnt`

### `ShopReplyDTO.java`
- **목적:** 상점 상품에 대한 리뷰/댓글 정보를 전송합니다.
- **주요 필드:** `srid`, `sid`, `mid`, `name`, `text`

### 이미지 정보 DTO (`PostImageDTO`, `ProfileImageDTO`, `ShopImageDTO`)
- **목적:** 게시물, 프로필, 상품 등에 포함된 이미지 정보를 전송합니다. 세 DTO는 구조적으로 동일합니다.
- **주요 필드:** `uuid`, `imgName`, `path`
- **주요 메서드:** `getImageURL()`, `getThumbnailURL()`는 이미지에 접근할 수 있는 인코딩된 URL을 생성합니다.

### 업로드 결과 DTO (`UploadResultDTO`, `ShopUploadResultDTO`)
- **목적:** 파일 업로드 성공 후, 생성된 파일 정보를 클라이언트에 반환합니다. 두 DTO는 구조적으로 동일합니다.
- **주요 필드:** `fileName`, `uuid`, `folderPath`
- **주요 메서드:** `getImageURL()`, `getThumbnailURL()`는 이미지 접근 URL을 생성합니다.

---

# JPA Entity 분석

이 섹션은 데이터베이스 테이블과 매핑되는 JPA Entity 클래스들을 설명합니다.

### `BaseEntity.java`
- **목적:** 엔티티들의 공통 필드인 생성일과 수정일을 관리합니다.
- **특징:** `@MappedSuperclass`, `@EntityListeners(AuditingEntityListener.class)`를 통해 JPA Auditing 기능을 제공합니다.
- **주요 필드:** `regDate` (`@CreatedDate`), `modDate` (`@LastModifiedDate`)

### `Member.java`
- **목적:** `member` 테이블과 매핑되며, 사용자 정보를 저장합니다.
- **관계:** `Relation` (`@OneToMany`), `MemberRole` (`@ElementCollection`)

### `Post.java`
- **목적:** `post` 테이블과 매핑되며, 사용자가 작성한 게시물을 저장합니다.
- **관계:** `Member` (`@ManyToOne`)

### `PostImage.java`
- **목적:** `post_image` 테이블과 매핑되며, `Post`에 속한 이미지 정보를 저장합니다.
- **관계:** `Post` (`@ManyToOne`)

### `Profile.java`
- **목적:** `profile` 테이블과 매핑되며, `Member`와 연결된 확장 프로필 정보를 저장합니다.
- **관계:** `Member` (`@ManyToOne`)

### `ProfileImage.java`
- **목적:** `profile_image` 테이블과 매핑되며, `Profile`에 속한 이미지 정보를 저장합니다.
- **관계:** `Profile` (`@ManyToOne`)

### `Relation.java`
- **목적:** `relation` 테이블과 매핑되며, 회원 간의 팔로우 관계를 저장합니다.
- **관계:** `Member` (`@ManyToOne` - following, follower)

### `Reply.java`
- **목적:** `reply` 테이블과 매핑되며, `Post`에 달린 댓글을 저장합니다.
- **관계:** `Post` (`@ManyToOne`), `Member` (`@ManyToOne`)

### `Shop.java`
- **목적:** 판매 상품 정보를 저장합니다.
- **관계:** `Member` (`@ManyToOne`), `ShopCate` (`@ManyToOne`)

### `ShopCate.java`
- **목적:** 상품 카테고리 정보를 저장합니다.

### `ShopImage.java`
- **목적:** `Shop` 상품의 이미지 정보를 저장합니다.
- **관계:** `Shop` (`@ManyToOne`)

### `ShopReply.java`
- **목적:** `Shop` 상품에 대한 리뷰/댓글을 저장합니다.
- **관계:** `Shop` (`@ManyToOne`), `Member` (`@ManyToOne`)

---

# Repository 분석

이 섹션은 Spring Data JPA를 사용하여 데이터베이스와 상호작용하는 Repository 인터페이스들을 설명합니다.

### `MemberRepository.java`
- **대상 엔티티:** `Member`
- **주요 기능:** 이메일과 소셜 로그인 여부로 회원 조회 시 `@EntityGraph`를 사용하여 N+1 문제 방지. `QuerydslPredicateExecutor`를 통한 동적 쿼리 지원.

### `PostRepository.java`
- **대상 엔티티:** `Post`
- **주요 기능:** `@Query`를 통해 게시물, 대표 이미지, 댓글 수를 JOIN하여 목록 및 상세 정보를 한 번의 쿼리로 조회.

### `ReplyRepository.java`
- **대상 엔티티:** `Reply`
- **주요 기능:** `@EntityGraph`를 사용하여 댓글 조회 시 작성자 정보를 EAGER 로딩. `@Modifying`으로 특정 회원이 작성한 댓글 일괄 삭제.

### `ShopRepository.java`
- **대상 엔티티:** `Shop`
- **주요 기능:** `SearchShopRepository` 인터페이스를 상속받아 QueryDSL을 이용한 동적 검색 기능 구현.

### `ShopCateRepository.java`
- **대상 엔티티:** `ShopCate`
- **주요 기능:** 모든 카테고리 조회, 카테고리 이름으로 조회.

### `ShopImageRepository.java`
- **대상 엔티티:** `ShopImage`
- **주요 기능:** 특정 게시물 ID에 속한 모든 이미지 삭제, 특정 게시물 ID에 속한 모든 이미지 조회.

### `ShopReplyRepository.java`
- **대상 엔티티:** `ShopReply`
- **주요 기능:** 특정 게시물 ID에 속한 모든 댓글 삭제, 특정 게시물에 달린 댓글 조회 시 작성자 정보 함께 가져오기, 특정 회원이 작성한 모든 게시물 댓글 삭제.

### `SearchShopRepository.java` & `SearchShopRepositoryImpl.java`
- **목적:** QueryDSL을 사용한 동적 검색 기능 구현.
- **주요 로직:** `searchPage()` 메서드에서 `BooleanBuilder`로 동적 검색 조건을, `PathBuilder`로 동적 정렬 조건을 생성. `JPQLQuery`와 `Tuple`을 사용하여 여러 엔티티를 조합하고 `groupBy`로 집계. `PageImpl`로 페이지네이션 결과 반환.

---

# Spring Security 분석

이 섹션은 Spring Security와 관련된 클래스들을 설명하며, 인증, 인가, 필터, 핸들러 등을 포함합니다.

### `dto/AuthMemberDTO.java`
- **목적:** Spring Security의 인증/인가 프로세스에서 사용될 사용자 정보를 담는 핵심 DTO. `User`를 상속하고 `OAuth2User`를 구현하여 Form 로그인과 소셜 로그인을 모두 처리.

### `service/PamilyUserDetailsService.java`
- **목적:** 일반 Form 기반 로그인을 위한 `UserDetailsService` 구현체. `MemberRepository`에서 사용자를 조회하여 `AuthMemberDTO`로 변환.

### `service/OAuth2UserDetailsService.java`
- **목적:** OAuth2 소셜 로그인을 위한 `OAuth2UserService` 구현체. 소셜 로그인 성공 후 DB에서 사용자를 조회하거나 신규 생성하여 `AuthMemberDTO`로 반환.

### `handler/LoginSuccessHandler.java`
- **목적:** Form 기반 로그인 성공 후의 로직을 처리. 소셜 로그인 여부나 사용자의 권한에 따라 적절한 페이지로 리디렉션.

### `util/JWTUtil.java`
- **목적:** JWT(JSON Web Token) 생성 및 검증을 담당. `generateToken`으로 토큰을 생성하고 `validateAndExtract`로 검증 및 정보 추출.

### `filter/CORSFilter.java`
- **목적:** CORS 정책 관련 헤더를 설정하여 다른 도메인에서의 API 요청을 허용. 모든 필터 중 가장 먼저 실행.

### `filter/ApiLoginFilter.java`
- **목적:** API 방식의 로그인을 처리. 특정 URL의 요청을 받아 인증 성공 시 JWT를 생성하여 응답.

### `filter/ApiCheckFilter.java`
- **목적:** API URL에 대한 요청을 가로채 JWT 토큰의 유효성을 검사. `Authorization` 헤더를 확인하여 토큰이 유효하지 않으면 접근을 차단.

### `handler/ApiLoginFailHandler.java`
- **목적:** `ApiLoginFilter`에서 인증이 실패했을 때의 로직을 처리. `401 Unauthorized` 상태와 JSON 에러 메시지를 응답.

---

# Service (비즈니스 로직) 분석

이 섹션은 컨트롤러의 요청을 받아 실제 비즈니스 로직을 수행하는 서비스 계층의 클래스들을 설명합니다.

### `MemberService` / `MemberServiceImpl`
- **목적:** 회원 가입, 조회, 수정 등 회원 관련 비즈니스 로직을 처리.
- **주요 기능:** 신규 회원 가입 시 이메일 중복 확인 및 기본 권한 부여, 회원 정보 수정.

### `PostService` / `PostServiceImpl`
- **목적:** 게시물 관련 비즈니스 로직을 처리.
- **주요 기능:** `@Transactional`을 통해 게시물과 이미지를 함께 처리 (등록, 삭제, 수정).
- `removeWithShopImageAndReply`: 데이터 무결성을 위해 댓글->이미지->상품 순서로 삭제.
- `getList`: QueryDSL을 이용한 동적 검색 결과를 받아와 DTO로 변환.
- `modify`: 기존 이미지를 모두 삭제하고 새로운 이미지 목록을 저장하는 방식으로 수정.

### `ProfileService` / `ProfileServiceImpl`
- **목적:** 사용자 프로필(주로 프로필 이미지) 관련 로직을 처리. `PostService`와 유사한 구조.

### `RelationService` / `RelationServiceImpl`
- **목적:** 회원 간의 팔로우/언팔로우 관계를 관리.
- **주요 기능:** `Relation` 엔티티를 생성/삭제하여 팔로우/언팔로우 처리. QueryDSL을 이용한 회원 목록 검색.

### `ReplyService` / `ReplyServiceImpl`
- **목적:** 게시물 댓글의 CRUD 로직을 처리.

### `ShopService` / `ShopServiceImpl`
- **목적:** 상점 상품의 복합적인 비즈니스 로직을 처리.
- **주요 기능:**
  - `@Transactional`을 통해 상품, 이미지, 댓글을 함께 처리 (등록, 삭제, 수정).
  - `removeWithShopImageAndReply`: 데이터 무결성을 위해 댓글->이미지->상품 순서로 삭제.
  - `getList`: QueryDSL을 이용한 동적 검색 결과를 받아와 DTO로 변환.
  - `modify`: 기존 이미지를 모두 삭제하고 새로운 이미지 목록을 저장하는 방식으로 수정.

### `ShopCateService` / `ShopCateSeviceImpl`
- **목적:** 상점 상품 카테고리 관련 로직을 처리.

### `ShopReplyService` / `ShopReplyServiceImpl`
- **목적:** 상점 상품에 대한 댓글(리뷰)의 CRUD 로직을 처리. `ReplyService`와 유사한 구조.