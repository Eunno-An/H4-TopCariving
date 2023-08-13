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
import { TrimUrl, apiInstance } from '@utils/api';
import { TrimCardInterface } from '@components/myCar/trim';
import { myCarOptionInterface } from '@interface/index';
import { ArchiveDetail } from '@pages/Archive/detail';
import { Archive } from '@pages/Archive';
import { ArchiveMain } from '@pages/Archive/main';

export const router = createBrowserRouter([
  { path: '/', element: <Login /> },
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
      },
      {
        path: 'option',
        element: <MyCarOptions />,
      },
      {
        path: 'option/genuine',
        element: <Flex>H genuine</Flex>,
      },
      {
        path: 'option/performance',
        element: <Flex>N Performance</Flex>,
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
  { path: '/*', element: <Error /> },
]);
