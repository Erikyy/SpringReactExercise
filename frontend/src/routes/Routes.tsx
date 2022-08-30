import React from 'react';

import type { FC } from 'react';
import PackageCategoryService from '@app/service/PackageCategoryService';
import { useAppSelector } from '@app/store/RootStore';
import type { PackageCategory } from '@app/types/PackageCategory';
import { useQuery } from '@tanstack/react-query';
import BasePage from '@app/components/BasePage';
import { Route, Routes } from 'react-router-dom';
import PackagesPage from './PackagesPage';
import OrdersPage from './OrdersPage';

const AppRoutes: FC = () => {
  const { activeLang } = useAppSelector((state) => state.languages);

  const { isLoading, data, error } = useQuery<PackageCategory[]>(
    ['categories'],
    () => {
      return PackageCategoryService.getAllCategoriesByLanguage(activeLang);
    },
  );
  return (
    <Routes>
      <Route path='/' element={<BasePage />}>
        <Route index element={<PackagesPage />} />
        {data &&
          data.map((item, i) => {
            return (
              <Route
                key={`${item.id}-${item.name}-${i}`}
                path={`/${item.name}`}
                element={<PackagesPage />}
              />
            );
          })}

        <Route path='orders' element={<OrdersPage />} />
      </Route>
    </Routes>
  );
};
export default AppRoutes;
