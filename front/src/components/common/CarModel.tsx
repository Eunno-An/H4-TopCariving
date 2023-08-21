import styled from '@emotion/styled';
import { MouseEvent, useEffect, useState } from 'react';
import { Flex } from './Flex';
import { Text } from '.';
import rotationIcon from '@assets/images/rotationHorizontal.svg';

export const CarModel = ({ exteriorColor }: { exteriorColor: string }) => {
  const [carList, setCarList] = useState<{ path: string }[]>([]);
  const [focus, setFocus] = useState<number>(0);
  const [isClicked, setIsClicked] = useState<boolean>(false);
  const [pointerPosition, setPointerPosition] = useState<number>(
    window.innerWidth / 2,
  );
  const [rotateBtnClick, setRotateBtnClick] = useState(false);

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
    setRotateBtnClick(true);
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
    <CarModelBox
      width={503}
      height={355}
      onMouseDown={onMouseDownHandler}
      onMouseMove={onMouseMoveHandler}
      onMouseUp={onMouseOverHandler}
      onMouseLeave={onMouseLeaveHandler}
    >
      {!rotateBtnClick && (
        <RotateButton rotateBtnClick={rotateBtnClick} direction="column">
          <RotateTextInfo typo="Body2_Medium" palette="Black">
            360°
          </RotateTextInfo>
          <RotateImg src={rotationIcon} alt="" />
        </RotateButton>
      )}

      {carList.map((it, idx) => (
        <ImgContainer
          key={idx}
          src={it.path}
          style={{ display: focus === idx ? 'inline-block' : 'none' }}
        />
      ))}
      <CarBorder />
      <TextBox typo="Body2_Medium" palette="DarkGray">
        360°
      </TextBox>
    </CarModelBox>
  );
};

const RotateButton = styled(Flex)<{ rotateBtnClick: boolean }>`
  position: absolute;

  display: flex;
  justify-content: center;

  width: 75px;
  height: 75px;

  border: 1px solid #e4dcd3;
  border-radius: 50%;

  background-color: rgba(246, 243, 242, 0.6);

  z-index: 3;

  cursor: pointer;
`;

const RotateTextInfo = styled(Text)`
  position: absolute;
  top: 15px;
  left: 22px;
`;

const RotateImg = styled.img`
  position: absolute;

  top: 30px;
  left: 20px;
`;

const TextBox = styled(Text)`
  display: flex;
  justify-content: center;
  position: absolute;

  top: 320px;
  z-index: 1;
`;

const CarBorder = styled.div`
  position: absolute;
  width: 370px;
  height: 110px;

  top: 250px;
  left: 40px;

  border-radius: 50%;
  border: 1px solid #e4dcd3;
  background: #f6f3f2;

  transform: scale(2, 0.5);
`;

const CarModelBox = styled(Flex)`
  position: relative;
`;

const ImgContainer = styled.img`
  width: 800px;
  height: auto;

  object-fit: cover;

  z-index: 2;
`;
