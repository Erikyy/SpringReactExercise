import React, { useEffect } from 'react';
import type { FC } from 'react';
import { Container } from '@mui/material';

import { fetchCurrencies } from '@app/features/Currency/CurrencySlice';
import { fetchLanguages } from '@app/features/Language/LanguageSlice';
import { useAppDispatch } from '@app/store/RootStore';
import AppRoutes from '@app/routes/Routes';

const App: FC = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(fetchCurrencies());
    dispatch(fetchLanguages());
  }, [dispatch]);

  return (
    <Container maxWidth={false}>
      <AppRoutes />
    </Container>
  );
};

export default App;
