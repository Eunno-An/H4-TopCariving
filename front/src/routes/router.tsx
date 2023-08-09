import { Flex } from '@components/common';
import MyCar from '@pages/MyCar';
import BodyType from '@pages/MyCar/Trim/BodyType';
import { createBrowserRouter } from 'react-router-dom';
import Engine from '@pages/MyCar/Trim/Engine';
import { Trim } from '@pages/MyCar/Trim';
import Traction from '@pages/MyCar/Trim/Traction';
import { MyCarOptions } from '@pages/MyCar/Option';
import Login from '@pages/Login';
import Error from '@pages/Error';
import Color from '@pages/MyCar/Color';

export const router = createBrowserRouter([
  { path: '/', element: <Login /> },
  {
    path: '/my-car',
    element: <MyCar />,
    children: [
      {
        path: 'trim',
        element: <Trim />,
      },
      {
        path: 'trim/engine',
        element: <Engine />,
      },
      {
        path: 'trim/body-type',
        element: <BodyType />,
      },
      {
        path: 'trim/traction',
        element: <Traction />,
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
  { path: '/my-car/complete', element: <Flex>내 차 만들기 완성!!</Flex> },
  {
    path: '/archive',
    element: <Flex>아카이빙</Flex>,
  },
  { path: '/*', element: <Error /> },
]);
