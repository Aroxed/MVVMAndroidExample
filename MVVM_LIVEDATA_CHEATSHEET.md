# MVVM Android Cheatsheet (This Project)

## Quick Architecture Map

- **View**: `MainActivity` (UI only, observes state, handles clicks/navigation)
- **ViewModel**: `MainViewModel` (business/UI state logic, survives configuration changes)
- **Model/Data**: currently simple in-memory random number generation
- **Data flow**: Button click -> ViewModel updates `LiveData` -> Activity observer updates `TextView`

## LiveData Essentials

### What `LiveData` is

- Lifecycle-aware observable data holder.
- Notifies active UI observers when data changes.
- Prevents many common lifecycle crashes/leaks compared to manual listeners.

### Core APIs used here

- `MutableLiveData<T>`: writable data inside ViewModel.
- `LiveData<T>`: read-only exposure to UI.
- `observe(owner, observer)`: subscribe with lifecycle awareness.
- `setValue(value)`: update on main thread.
- `postValue(value)`: update from background thread.

### Pattern used in this app

```java
public class MainViewModel extends ViewModel {
    private final MutableLiveData<Integer> randomNumber = new MutableLiveData<>();

    public LiveData<Integer> getRandomNumber() {
        return randomNumber; // expose immutable type
    }

    public void generateNewRandomNumber() {
        randomNumber.setValue(new Random().nextInt(100));
    }
}
```

```java
viewModel.getRandomNumber().observe(this, number -> {
    if (number != null) {
        tvRandomNumber.setText(String.valueOf(number));
    }
});
```

### Best practices

- Keep `MutableLiveData` private; expose `LiveData`.
- Do not put Android `View` references in ViewModel.
- Let Activity/Fragment render state; do not render from ViewModel.
- Prefer one source of truth for each UI state field.
- Use `postValue` only when updating from worker thread.

### Common pitfalls

- Re-observing incorrectly (duplicate observers in wrong lifecycle owner).
- Doing heavy work in observer block.
- Storing transient UI references in ViewModel.
- Treating LiveData as event bus (use event wrappers/`SharedFlow` for one-time events).

## ViewModel Cheatsheet

### Why it matters

- Survives configuration changes (e.g., rotation).
- Separates UI logic from Activity lifecycle complexity.
- Improves testability by moving logic out of Activity.

### In this project

- `mvvm` branch introduces `MainViewModel`.
- `mvvm_lifecycle` keeps same ViewModel but adds lifecycle logs in Activity.
- `mvvm_lifecycle_save_state` extends ViewModel with `numberHistory`.

## Lifecycle Cheatsheet

### Main callbacks

- `onCreate`: initial setup.
- `onStart`: UI visible.
- `onResume`: UI interactive.
- `onPause`: partially obscured.
- `onStop`: not visible.
- `onDestroy`: cleanup/final teardown.
- `onSaveInstanceState`: save small UI state bundle.
- `onRestoreInstanceState`: restore bundle state.

### Why lifecycle awareness matters with LiveData

- `observe(this, ...)` ties updates to Activity lifecycle.
- Observer receives updates only when owner is active.
- Reduces need for manual unsubscribe logic.

## Saved State vs ViewModel

### Rule of thumb

- **ViewModel**: retain in-memory state during configuration changes.
- **SavedInstanceState**: recover critical small state after process death.

### In this project

- `mvvm_lifecycle_save_state` saves `numberHistory` in `Bundle` and restores it.
- History is then synced back into `MainViewModel`.

## Feature-by-Branch Snapshot

- **`master`**: no ViewModel/LiveData; Activity handles random generation directly.
- **`mvvm`**: introduces `MainViewModel` + `LiveData` observer pattern.
- **`mvvm_lifecycle`**: adds detailed lifecycle logging.
- **`mvvm_lifecycle_save_state`**: adds number history + saved/restored instance state.

## Practical Checklist

- [ ] Put UI state in ViewModel.
- [ ] Expose immutable `LiveData` from ViewModel.
- [ ] Observe from Activity/Fragment with correct lifecycle owner.
- [ ] Keep observers lightweight (render only).
- [ ] Save minimal critical data in `onSaveInstanceState`.
- [ ] Restore state and rebind UI after recreation.

## Next-Level Upgrades (Optional)

- Replace `LiveData` with `StateFlow`/`SharedFlow` for Kotlin-first architecture.
- Add Repository layer for real data source separation.
- Add unit tests for ViewModel logic and history behavior.
- Use `SavedStateHandle` in ViewModel for cleaner state persistence.
