# Lango — Android Architecture

## Direction

Android-first, mobile-native, local-first where practical.

Initial stack:
- Kotlin
- Jetpack Compose
- Room/SQLite for durable learner state
- DataStore for preferences
- Retrofit/OkHttp or Ktor for network access
- AI provider abstraction

## Core principle

The application must not be coupled to a single AI provider.

Use an abstraction such as:

LanguageIntelligenceProvider

The app's learning/domain logic should request capabilities, not provider-specific APIs.

## Initial domain boundaries

- learner
- curriculum
- sessions
- conversation
- pronunciation
- pragmatics/culture
- intelligence

Avoid premature multi-module fragmentation. Keep conceptual boundaries clear while allowing a small initial Android project.

## Offline/local state

Persist:
- goals
- learner state
- item mastery
- errors
- session history
- preferences

Cloud accounts/synchronization can follow after the core loop is validated.

## First architecture test

The app must be able to:
1. create a learner profile
2. create a German goal
3. select/generate today's session
4. run a session
5. record outcomes
6. update learner state
7. produce a changed recommendation next time
