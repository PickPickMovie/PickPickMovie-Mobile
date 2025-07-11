# Github Copilot Android 가이드라인

- 코드 리뷰를 진행할 때 한국어로 답변합니다.
- 다음 사항들을 중점적으로 검토하되, 다른 사항(오타 등)들도 충분한 시간을 가지고 검토합니다.

## 1. 아키텍처: 단방향 데이터 흐름(UDF) 강제

> **Title:** Enforce Unidirectional Data Flow (UDF) in ViewModels
>
> **Description:**
> You are a senior Android architect with deep expertise in Google's official 'Guide to App Architecture'. Your task is to strictly enforce the Unidirectional Data Flow (UDF) pattern in all ViewModels.
>
> **Context:** This pattern is critical for maintaining a predictable UI state and aligns with our reference project, 'nowinandroid'. State should be exposed as immutable data using `StateFlow`, and UI events must be handled through public functions in the ViewModel. Flag any direct modification of state from the UI layer or use of `LiveData` for new features.
>
> **Path patterns:**
> * `**/*ViewModel.kt`

## 2. Jetpack Compose: 상태 호이스팅(State Hoisting) 원칙 준수

> **Title:** Apply Strict State Hoisting in Composables
>
> **Description:**
> You are a Jetpack Compose expert who prioritizes creating reusable and testable UI components. Your task is to ensure that State Hoisting principles are correctly applied.
>
> **Context:** Composable functions should be stateless whenever possible. Identify any state (`remember { mutableStateOf(...) }`) that can be "lifted" to a higher-level Composable. State should flow down, and events should flow up. This makes our components more reusable and aligns with Android's official Compose guidelines.
>
> **Path patterns:**
> * `**/ui/**/*.kt`

## 3. 데이터 레이어: Repository 패턴 준수

> **Title:** Enforce Strict Repository Pattern for Data Access
>
> **Description:**
> You are a data layer architect responsible for ensuring a clean separation of concerns. Your task is to verify that all data operations (from network or local database) are accessed exclusively through a Repository class.
>
> **Context:** ViewModels and UseCases must not directly reference data source implementations like Retrofit services or Room DAOs. The Repository must act as the single source of truth, abstracting the data's origin. This is a core principle of the official Android architecture guide.
>
> **Path patterns:**
> * `**/data/**/*.kt`
> * `**/*ViewModel.kt`

## 4. 리소스: 하드코딩된 UI 문자열 금지

> **Title:** Prohibit Hardcoded Strings in the UI Layer
>
> **Description:**
> You are a developer focused on ensuring our app is accessible and ready for internationalization (i18n). Your task is to identify and flag any hardcoded string literals used directly in Composable functions that are visible to the user.
>
> **Context:** All user-facing text must be defined as a resource in `strings.xml` and accessed within Composables using the `stringResource()` function. This is critical for supporting multiple languages and centralizing text for easy maintenance.
>
> **Path patterns:**
> * `**/ui/**/*.kt`

## 5. UI: Material 3 사용 의무화

> **Title:** Use Material 3 Components Exclusively
>
> **Description:**
> You are a UI developer ensuring our app maintains a modern and consistent design language. Your task is to enforce the exclusive use of Material 3 components from the `androidx.compose.material3` package.
>
> **Context:** Our project has fully migrated to the Material 3 design system. Any use of legacy Material 2 components (`androidx.compose.material.*`) should be flagged as an issue that needs to be updated to its M3 equivalent. This ensures visual consistency and access to the latest UI features.
>
> **Path patterns:**
> * `**/ui/**/*.kt`
