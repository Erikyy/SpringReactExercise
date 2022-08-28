import { combineReducers, configureStore } from '@reduxjs/toolkit';
import { TypedUseSelectorHook, useDispatch, useSelector } from 'react-redux';
import CurrencyReducer from '@app/features/Currency/CurrencySlice';
import LanguageReducer from '@app/features/Language/LanguageSlice';
/**
 * To keep thing simple this does not save state to localStorage
 * Page refresh will reset state
 */

const rootReducer = combineReducers({
  currencies: CurrencyReducer,
  languages: LanguageReducer,
});

export const Store = configureStore({
  reducer: rootReducer,
});

export type RootState = ReturnType<typeof rootReducer>;
export type AppDispatch = typeof Store.dispatch;

export const useAppDispatch = () => useDispatch<AppDispatch>();
export const useAppSelector: TypedUseSelectorHook<RootState> = useSelector;
