import {
  AnyAction,
  combineReducers,
  configureStore,
  Store,
} from '@reduxjs/toolkit';
import { render } from '@testing-library/react';
import React, { PropsWithChildren } from 'react';
import {
  Provider,
  ProviderProps,
  TypedUseSelectorHook,
  useDispatch,
  useSelector,
} from 'react-redux';

import translationsEN from '@app/translations/en.json';
import translationsEE from '@app/translations/ee.json';

import { I18nextProvider, initReactI18next } from 'react-i18next';
import i18n from 'i18next';

import LanguageReducer from '@app/features/Language/LanguageSlice';
import CurrencyReducer from '@app/features/Currency/CurrencySlice';

i18n.use(initReactI18next).init({
  resources: {
    en: {
      translation: translationsEN,
    },
    et: {
      translation: translationsEE,
    },
  },
  fallbackLng: 'en',
  lng: 'en',
  interpolation: {
    escapeValue: false,
  },
  keySeparator: false,
});

export function renderWithProviders(
  component: React.ReactElement,
  store: Store<any, AnyAction>,
) {
  function Wrapper({ children }: PropsWithChildren<{}>): JSX.Element {
    return (
      <Provider store={store}>
        <I18nextProvider i18n={i18n}>{children}</I18nextProvider>
      </Provider>
    );
  }
  const ui = render(component, { wrapper: Wrapper });
  return { store, ui };
}

const rootReducer = combineReducers({
  languages: LanguageReducer,
  currencies: CurrencyReducer,
});
export const mockStore = configureStore({
  reducer: rootReducer,
});
export type MockState = ReturnType<typeof rootReducer>;
export type MockDispatch = typeof mockStore.dispatch;

export const mockAppDispatch = () => useDispatch<MockDispatch>();
export const mockAppSelector: TypedUseSelectorHook<MockState> = useSelector;
