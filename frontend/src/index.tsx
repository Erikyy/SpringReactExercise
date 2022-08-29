import React from 'react';
import { createRoot } from 'react-dom/client';
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
const rootElem = document.getElementById('root');
if (!rootElem) throw new Error('Failed to find the root element');

const root = createRoot(rootElem);
root.render(
  <React.StrictMode>
    <Provider store={Store}>
      <QueryClientProvider client={client}>
        <BrowserRouter>
          <App />
        </BrowserRouter>
      </QueryClientProvider>
    </Provider>
  </React.StrictMode>,
);
