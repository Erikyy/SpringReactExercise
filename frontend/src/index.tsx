import React from 'react';
import ReactDOM from 'react-dom';
import App from './app/App';
import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { BrowserRouter } from 'react-router-dom';
import './index.css';
import i18next, { i18n } from 'i18next';
import { Provider } from 'react-redux';
import { initReactI18next } from 'react-i18next';
import translationsEN from './translations/en.json';
import translationsEE from './translations/ee.json';
import { Store } from './store/RootStore';

i18next.use(initReactI18next).init({
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

const client = new QueryClient();

ReactDOM.render(
  <React.StrictMode>
    <Provider store={Store}>
      <QueryClientProvider client={client}>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </QueryClientProvider>
    </Provider>
  </React.StrictMode>,
  document.getElementById('root'),
);

// Hot Module Replacement (HMR) - Remove this snippet to remove HMR.
// Learn more: https://snowpack.dev/concepts/hot-module-replacement
if (import.meta.hot) {
  import.meta.hot.accept();
}
