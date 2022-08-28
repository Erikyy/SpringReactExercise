import React, { useEffect } from 'react';
import type { FC } from 'react';
import { Container, Typography } from '@mui/material';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useQuery } from '@tanstack/react-query';
import type { Order } from '@app/types/Order';
import OrderService from '@app/service/OrderService';
import { useAppSelector } from '@app/store/RootStore';
import getSymbolFromCurrency from 'currency-symbol-map';
import { useTranslation } from 'react-i18next';
import { T_PACKAGE_CATEGORY, T_PACKAGE_PRICE, T_STATUS } from '@app/constants';

const OrdersPage: FC = () => {
  const { t } = useTranslation();
  const activeCurrency = useAppSelector(
    (state) => state.currencies.activeCurrency,
  );
  const { isLoading, data, error, refetch } = useQuery<any>(
    ['orders'],
    async () => {
      return await OrderService.getOrders(activeCurrency);
    },
  );

  useEffect(() => {
    refetch();
  }, [activeCurrency]);

  if (isLoading) {
    return null;
  }

  const cols: GridColDef[] = [
    { field: 'id', headerName: 'Id' },
    { field: 'status', headerName: t(T_STATUS), width: 150 },
    {
      field: 'packageEntity',
      headerName: 'Package Type',
      width: 150,
      valueGetter(params) {
        console.log(params);
        return params.value.packageType;
      },
    },
    {
      field: 'packageEntity2',
      headerName: t(T_PACKAGE_PRICE),
      width: 150,
      valueGetter({ id }) {
        const item = data.find((item: Order) => item.id === id);
        console.log(item);
        return `${item.packageEntity.price} ${getSymbolFromCurrency(
          activeCurrency,
        )}`;
      },
    },
    {
      field: 'packageEntity3',
      headerName: t(T_PACKAGE_CATEGORY),
      width: 150,
      valueGetter({ id }) {
        const item = data.find((item: Order) => item.id === id);
        console.log(item);
        return item.packageEntity.category;
      },
    },
  ];
  return (
    <Container sx={{ height: 500 }}>
      <DataGrid
        rows={data}
        columns={cols}
        pageSize={10}
        rowsPerPageOptions={[5]}
      />
    </Container>
  );
};

export default OrdersPage;
