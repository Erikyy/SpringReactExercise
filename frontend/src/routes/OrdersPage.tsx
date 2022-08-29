import React, { useEffect } from 'react';
import type { FC } from 'react';
import {
  Box,
  CircularProgress,
  Container,
  Icon,
  IconButton,
  Typography,
} from '@mui/material';
import { DataGrid, GridColDef } from '@mui/x-data-grid';
import { useQuery } from '@tanstack/react-query';
import type { Order } from '@app/types/Order';
import OrderService from '@app/service/OrderService';
import { useAppSelector } from '@app/store/RootStore';
import getSymbolFromCurrency from 'currency-symbol-map';
import { useTranslation } from 'react-i18next';
import {
  T_PACKAGE_CATEGORY,
  T_PACKAGE_PRICE,
  T_PACKAGE_TYPE,
  T_STATUS,
} from '@app/constants';
import Loading from '@app/components/common/Loading';
import type { Description } from '@app/types/Description';

const OrdersPage: FC = () => {
  const { t } = useTranslation();
  const { languages, currencies } = useAppSelector((state) => state);
  const { isLoading, data, error, refetch } = useQuery<any>(
    ['orders'],
    async () => {
      return await OrderService.getOrders(currencies.activeCurrency);
    },
  );

  useEffect(() => {
    refetch();
  }, [currencies.activeCurrency]);

  if (isLoading) {
    return <Loading />;
  }

  const cols: GridColDef[] = [
    { field: 'id', headerName: 'Id' },
    { field: 'status', headerName: t(T_STATUS), width: 150 },
    {
      field: 'packageEntity',
      headerName: t(T_PACKAGE_TYPE),
      width: 150,
      valueGetter(params) {
        return params.value.packageType;
      },
    },
    {
      field: 'packageEntity2',
      headerName: t(T_PACKAGE_PRICE),
      width: 150,
      valueGetter({ id }) {
        const item = data.find((item: Order) => item.id === id);
        return `${item.packageEntity.price} ${getSymbolFromCurrency(
          currencies.activeCurrency,
        )}`;
      },
    },
    {
      field: 'packageEntity3',
      headerName: t(T_PACKAGE_CATEGORY),
      width: 150,
      valueGetter({ id }) {
        const item = data.find((item: Order) => item.id === id);
        //this is the other way to get correct translations
        let content = item.packageEntity.category.descriptions.filter(
          (desc: Description) => desc.language === languages.activeLang,
        );
        return content[0].content;
      },
    },
  ];
  return (
    <Container sx={{ height: 500 }}>
      <IconButton onClick={() => refetch()}>
        <Icon fontSize='medium'>refresh</Icon>
      </IconButton>

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
