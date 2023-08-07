import { Flex } from '@components/common';
import MyCar from '@pages/MyCar';
import BodyType from '@pages/MyCar/Trim/BodyType';
import { createBrowserRouter } from 'react-router-dom';
import Engine from '@pages/MyCar/Trim/Engine';
import { Trim } from '@pages/MyCar/Trim';
import Traction from '@pages/MyCar/Trim/Traction';
import Login from '@pages/Login';

export const router = createBrowserRouter([
  { path: '', element: <Login /> },
  { path: '/*', element: <Flex>Error: 해당 url에 화면이 없습니다</Flex> },
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
        element: <Flex>컬러선택</Flex>,
      },
      {
        path: 'option',
        element: <Flex> 선택 옵션</Flex>,
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
]);
