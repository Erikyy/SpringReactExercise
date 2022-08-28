import React, { useEffect } from 'react';
import type { FC } from 'react';
import { Box, Container } from '@mui/material';
import { Route, Routes } from 'react-router-dom';
import BasePage from '@app/common/BasePage';
import HomePage from '@app/routes/HomePage';
import OrdersPage from '@app/routes/OrdersPage';
import OrderPage from '@app/routes/Orders/OrderPage';
import { useDispatch } from 'react-redux';
import { fetchCurrencies } from '@app/features/Currency/CurrencySlice';
import { fetchLanguages } from '@app/features/Language/LanguageSlice';
import { useAppDispatch } from '@app/store/RootStore';

const App: FC = () => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(fetchCurrencies());
    dispatch(fetchLanguages());
  }, [dispatch]);

  return (
    <Container maxWidth={false} sx={{ padding: '0 !important' }}>
      <Routes>
        <Route path='/' element={<BasePage />}>
          <Route index element={<HomePage />} />
          <Route path=':packageId' />
          <Route path='orders' element={<OrdersPage />}>
            <Route path=':orderId' element={<OrderPage />} />
          </Route>
        </Route>
      </Routes>
    </Container>
  );
};

export default App;
