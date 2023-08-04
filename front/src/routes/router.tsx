import { createBrowserRouter } from 'react-router-dom';
import MyCar from '../pages/MyCar';
import Trim from '../pages/MyCar/Trim';

export const router = createBrowserRouter([
  { path: '', element: <>메인화면</> },
  {
    path: '/my-car',
    element: <MyCar />,
    children: [
      {
        path: 'trim',
        element: <Trim />,
        children: [
          { path: 'engine', element: <>엔진 선택 화면</> },
          { path: 'bodytype', element: <>바디타입 선택 화면</> },
          { path: 'traction', element: <>구동방식 선택 화면</> },
        ],
      },

      {
        path: 'color',
        element: <>컬러선택</>,
      },
      {
        path: 'option',
        element: <> 선택 옵션</>,
        children: [
          { path: 'genuine', element: <>H genuine</> },
          { path: 'performance', element: <>N Performance</> },
        ],
      },
    ],
  },
  { path: '/my-car/complete', element: <>내 차 만들기 완성!!</> },
  {
    path: '/archive',
    element: <>아카이빙</>,
  },
]);
