import styled from '@emotion/styled';
import { MouseEvent, useEffect, useState } from 'react';
import { Flex } from './Flex';

export const CarModel = ({ exteriorColor }: { exteriorColor: string }) => {
  const [carList, setCarList] = useState<{ path: string }[]>([]);
  const [focus, setFocus] = useState<number>(0);
  const [isClicked, setIsClicked] = useState<boolean>(false);
  const [pointerPosition, setPointerPosition] = useState<number>(
    window.innerWidth / 2,
  );
  useEffect(() => {
    let newCarList = [] as { path: string }[];

    for (let idx = 1; idx <= 60; idx++) {
      const num = idx < 10 ? '00' + idx : '0' + idx;
      const path = `/image/exterior/${exteriorColor}/image_${num}.png`;
      newCarList = [...newCarList, { path }];
    }

    setCarList(newCarList);
  }, [exteriorColor]);

  const onMouseDownHandler = (e: MouseEvent<HTMLDivElement>) => {
    setIsClicked(true);
    setPointerPosition(e.screenX);
  };
  const onMouseOverHandler = () => {
    setIsClicked(false);
  };

  const onMouseMoveHandler = (e: MouseEvent<HTMLDivElement>) => {
    if (isClicked && pointerPosition > e.screenX + 5) {
      setFocus((focus + 1) % 60);
      setPointerPosition(e.screenX);
    } else if (isClicked && pointerPosition < e.screenX - 5) {
      focus - 1 < 0 ? setFocus(59) : setFocus(focus - 1);
      setPointerPosition(e.screenX);
    }
  };

  const onMouseLeaveHandler = () => {
    setIsClicked(false);
  };

  return (
    <Flex
      width={503}
      height={355}
      onMouseDown={onMouseDownHandler}
      onMouseMove={onMouseMoveHandler}
      onMouseUp={onMouseOverHandler}
      onMouseLeave={onMouseLeaveHandler}
    >
      {carList.map((it, idx) => (
        <ImgContainer
          key={idx}
          src={it.path}
          style={
            focus === idx ? { display: 'inline-block' } : { display: 'none' }
          }
        />
      ))}
    </Flex>
  );
};

const ImgContainer = styled.img`
  width: 800px;
  height: auto;
`;
