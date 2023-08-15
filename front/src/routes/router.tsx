import { Flex } from '@components/common';
import MyCar from '@pages/MyCar';
import BodyType from '@pages/MyCar/Trim/BodyType';
import { createBrowserRouter } from 'react-router-dom';
import Engine, { engineInfoInterface } from '@pages/MyCar/Trim/Engine';
import { Trim } from '@pages/MyCar/Trim';
import Traction from '@pages/MyCar/Trim/Traction';
import { MyCarOptions } from '@pages/MyCar/Option';
import Login from '@pages/Login';
import Error from '@pages/Error';
import Color from '@pages/MyCar/Color';
import Complete from '@pages/MyCar/Complete';

import { OptionUrl, ColorUrl, TrimUrl, apiInstance } from '@utils/api';
import { TrimCardInterface } from '@components/myCar/trim';
import { myCarOptionInterface } from '@interface/index';
import { ArchiveDetail } from '@pages/Archive/detail';
import { Archive } from '@pages/Archive';
import { ArchiveMain } from '@pages/Archive/main';
import { colorInfoInterface } from '@pages/MyCar/Color/interface';

export const router = createBrowserRouter([
  { path: '/', element: <Login /> },
  { path: '/*', element: <Error /> },
  {
    path: '/my-car',
    element: <MyCar />,
    children: [
      {
        path: 'trim',
        element: <Trim />,
        loader: async () => {
          return (await apiInstance({
            url: TrimUrl.MODELS,
            method: 'GET',
          })) as TrimCardInterface[];
        },
      },
      {
        path: 'trim/engine',
        element: <Engine />,
        loader: async () => {
          return (await apiInstance({
            url: TrimUrl.ENGINES,
            method: 'GET',
          })) as engineInfoInterface[];
        },
      },
      {
        path: 'trim/body-type',
        element: <BodyType />,
        loader: async () => {
          return (await apiInstance({
            url: TrimUrl.BODY_TYPE,
            method: 'GET',
          })) as myCarOptionInterface[];
        },
      },
      {
        path: 'trim/traction',
        element: <Traction />,
        loader: async () => {
          return (await apiInstance({
            url: TrimUrl.TRACTION,
            method: 'GET',
          })) as myCarOptionInterface[];
        },
      },
      {
        path: 'color',
        element: <Color />,
        loader: async () => {
          return (await apiInstance({
            url: ColorUrl.BOTH,
            method: 'GET',
          })) as colorInfoInterface;
        },
      },
      {
        path: 'option',
        element: <MyCarOptions key={'option'} />,
        loader: async () => {
          const res1 = await apiInstance({
            url: OptionUrl.SELECTION,
            method: 'GET',
          });
          const res2 = await apiInstance({
            url: OptionUrl.BASIC,
            method: 'GET',
          });

          const [selectOptionData, defaultData] = await Promise.all([
            res1,
            res2,
          ]);

          return {
            selectOptionData: selectOptionData,
            defaultOptionData: defaultData.data,
          };
        },
      },
      {
        path: 'option/genuine',
        element: <MyCarOptions key={'genuine'} />,
        loader: async () => {
          const res1 = await apiInstance({
            url: OptionUrl.ACCESSORY,
            method: 'GET',
          });
          const res2 = await apiInstance({
            url: OptionUrl.BASIC,
            method: 'GET',
          });

          const [selectOptionData, defaultData] = await Promise.all([
            res1,
            res2,
          ]);

          return {
            selectOptionData: selectOptionData,
            defaultOptionData: defaultData.data,
          };
        },
      },
      {
        path: 'option/performance',
        element: <MyCarOptions key={'performance'} />,
        loader: async () => {
          const res1 = await apiInstance({
            url: OptionUrl.PERFORMANCE,
            method: 'GET',
          });
          const res2 = await apiInstance({
            url: OptionUrl.BASIC,
            method: 'GET',
          });

          const [selectOptionData, defaultData] = await Promise.all([
            res1,
            res2,
          ]);

          return {
            selectOptionData: selectOptionData,
            defaultOptionData: defaultData.data,
          };
        },
      },
    ],
  },
  { path: '/my-car/complete', element: <Complete /> },
  {
    path: '/my-archive',
    element: <Flex>마이 카이빙</Flex>,
  },
  {
    path: '/archive',
    element: <Archive />,
    children: [
      {
        path: '',
        element: <ArchiveMain />,
      },
      {
        path: 'detail',
        element: <ArchiveDetail />,
      },
    ],
  },
]);
