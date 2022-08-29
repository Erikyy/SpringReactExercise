import React, { useEffect, useRef } from 'react';
import type { FC } from 'react';
import { Container } from '@mui/material';

import { fetchCurrencies } from '@app/features/Currency/CurrencySlice';
import { fetchLanguages } from '@app/features/Language/LanguageSlice';
import { useAppDispatch, useAppSelector } from '@app/store/RootStore';
import AppRoutes from '@app/routes/Routes';

const App: FC = () => {
  const dispatch = useAppDispatch();
  const { currencies, languages } = useAppSelector((state) => state);
  const shoudlFetchRef = useRef(true);

  useEffect(() => {
    if (shoudlFetchRef.current) {
      shoudlFetchRef.current = false;
      dispatch(fetchCurrencies());
      dispatch(fetchLanguages());
    }
  }, [dispatch]);

  return (
    <Container maxWidth={false}>
      <AppRoutes />
    </Container>
  );
};

export default App;
