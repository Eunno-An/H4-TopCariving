import { Flex, Text, Button } from '@components/common';
import { useMyCar } from '@contexts/MyCarContext';
import { css } from '@emotion/react';
import styled from '@emotion/styled';
import { myCarUrl } from '@pages/MyCar';
import { theme } from '@styles/theme';
import { ColorUrl, OptionUrl, TrimUrl, apiInstance } from '@utils/api';
import { Dispatch, SetStateAction, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { DetailOptionModal } from './option/DetailOptionModal';

interface footerProps {
  currentUrl: string;
  setCurrentUrl: Dispatch<SetStateAction<string>>;
}

export const getArchivingId = () => {
  const archivingId = localStorage.getItem('archivingId');
  return archivingId ? JSON.parse(archivingId) : null;
};

export const Footer = ({ currentUrl, setCurrentUrl }: footerProps) => {
  const navigate = useNavigate();
  const { myCarInfo } = useMyCar();

  const onClickButton = async (moveNum: number) => {
    const nextIdx = myCarUrl.indexOf(currentUrl) + moveNum;
    const nextUrl = myCarUrl[nextIdx];

    navigate(nextUrl);
    setCurrentUrl(nextUrl);
  };

  const [isOpenDetailModal, setIsOpenDetailModal] = useState(false);

  const setLocalData = () => {
    localStorage.setItem('myCarInfo', JSON.stringify(myCarInfo));
  };

  const postData = async () => {
    switch (currentUrl) {
      case '/my-car/trim':
        try {
          const archivingId = await apiInstance({
            url: `${TrimUrl.MODELS}`,
            method: 'POST',
            bodyData: JSON.stringify({
              archivingId: null,
              carOptionId: myCarInfo.trim.type?.id,
            }),
          });
          localStorage.setItem('archivingId', archivingId.data);
          onClickButton(+1);
        } catch (err) {
          console.error(err);
        }
        break;
      case '/my-car/trim/engine':
        try {
          await apiInstance({
            url: `${TrimUrl.ENGINES}?carOptionId=${myCarInfo.trim.engine?.id}`,
            method: 'POST',
            bodyData: JSON.stringify({
              archivingId: getArchivingId(),
              carOptionId: myCarInfo.trim.engine?.id,
            }),
          });
          onClickButton(+1);
        } catch (err) {
          console.error(err);
        }
        break;

      case '/my-car/trim/body-type':
        try {
          await apiInstance({
            url: `${TrimUrl.BODY_TYPE}?carOptionId=${myCarInfo.trim.bodyType?.id}`,
            method: 'POST',
            bodyData: JSON.stringify({
              archivingId: getArchivingId(),
              carOptionId: myCarInfo.trim.bodyType?.id,
            }),
          });
          onClickButton(+1);
        } catch (err) {
          console.error(err);
        }
        break;

      case '/my-car/trim/traction':
        try {
          await apiInstance({
            url: `${TrimUrl.TRACTION}?carOptionId=${myCarInfo.trim.traction?.id}`,
            method: 'POST',
            bodyData: JSON.stringify({
              archivingId: getArchivingId(),
              carOptionId: myCarInfo.trim.traction?.id,
            }),
          });
          onClickButton(+1);
        } catch (err) {
          console.error(err);
        }
        break;

      case '/my-car/color':
        try {
          await apiInstance({
            url: `${ColorUrl.BOTH}`,
            method: 'POST',
            bodyData: JSON.stringify({
              archivingId: getArchivingId(),
              exteriorColorOptionId: myCarInfo.color.exteriorColor?.id,
              interiorColorOptionId: myCarInfo.color.interiorColor?.id,
            }),
          });
          onClickButton(+1);
        } catch (err) {
          console.error(err);
        }
        break;

      case '/my-car/option':
        try {
          await apiInstance({
            url: `${OptionUrl.SELECTION}`,
            method: 'POST',
            bodyData: JSON.stringify({
              archivingId: getArchivingId(),
              ids: myCarInfo.option.selected.map((item) => item.id),
            }),
          });
          onClickButton(+1);
        } catch (err) {
          console.error(err);
        }
        break;

      case '/my-car/option/genuine':
        try {
          await apiInstance({
            url: `${OptionUrl.ACCESSORY}`,
            method: 'POST',
            bodyData: JSON.stringify({
              archivingId: getArchivingId(),
              ids: myCarInfo.option.genuine.map((item) => item.id),
            }),
          });
          onClickButton(+1);
        } catch (err) {
          console.error(err);
        }
        break;

      case '/my-car/option/performance':
        try {
          await apiInstance({
            url: `${OptionUrl.PERFORMANCE}`,
            method: 'POST',
            bodyData: JSON.stringify({
              archivingId: getArchivingId(),
              carOptionId: myCarInfo.option.performance.length
                ? myCarInfo.option.performance[0].id
                : null,
            }),
          });
          onClickButton(+1);
        } catch (err) {
          console.error(err);
        }
        break;

      default:
        onClickButton(+1);
    }
  };

  return (
    <Flex
      backgroundColor="Sand"
      height={108}
      borderRadius="16px 16px"
      padding="12px 36px 24px 36px"
      justify="center"
    >
      <Flex width={1280} gap={20}>
        <Flex gap={18}>
          <Section width={135}>
            <Text typo="Body3_Regular" palette="DarkGray">
              트림
            </Text>
            <Text typo={'Heading4_Bold'} palette="Black">
              {myCarInfo.trim.type?.name}
            </Text>
            <Flex justify="flex-start">
              <Text typo={'Body3_Regular'} palette="Black">
                {Object.values(myCarInfo.trim)
                  .map((trim, idx) =>
                    idx !== 0 && trim !== null ? trim.name : null,
                  )
                  .filter((name) => name !== null)
                  .join('/')}
              </Text>
            </Flex>
          </Section>
          <ColumnImg src="/image/page/myCar/columnLine.svg" />
          <Section width={191}>
            <Text typo="Body3_Regular" palette="DarkGray">
              선택 색상
            </Text>
            <Flex justify="start" gap={5}>
              <Text typo="Body3_Medium" palette="Black">
                외장
              </Text>
              {myCarInfo.color.exteriorColor?.url && (
                <ColorCircle src={myCarInfo.color.exteriorColor?.url} />
              )}
              <Text typo="Body3_Regular">
                {myCarInfo.color.exteriorColor?.name}
              </Text>
            </Flex>
            <Flex justify="start" gap={5}>
              <Text typo="Body3_Medium" palette="Black">
                내장
              </Text>
              {myCarInfo.color.exteriorColor?.url && (
                <ColorCircle src={myCarInfo.color.interiorColor?.url} />
              )}
              <Text typo="Body3_Regular">
                {myCarInfo.color.interiorColor?.name}
              </Text>
            </Flex>
          </Section>
          <ColumnImg src="/image/page/myCar/columnLine.svg" />
          <Section width={324}>
            <Text typo="Body3_Regular" palette="DarkGray">
              선택 옵션
            </Text>
            {Object.values(myCarInfo.option) && (
              <Flex
                gap={8}
                justify="flex-start"
                align="center"
                width={344}
                css={css`
                  overflow: auto;
                  ::-webkit-scrollbar {
                    display: none;
                  }
                `}
              >
                {Object.values(myCarInfo.option)
                  .flatMap((optionArray) => optionArray)
                  .slice(0, 3)
                  .map(
                    (option, idx) =>
                      option != null && (
                        <BlackTagChip key={`chip_${idx}`}>
                          <SelectOptionText>{option.name}</SelectOptionText>
                        </BlackTagChip>
                      ),
                  )}
                {Object.values(myCarInfo.option).flatMap(
                  (optionArray) => optionArray,
                ).length -
                  3 >
                  0 && (
                  <BlackTagChip
                    onClick={() => setIsOpenDetailModal(true)}
                    css={css`
                      cursor: pointer;
                    `}
                  >{`+${
                    Object.values(myCarInfo.option).reduce(
                      (acc, curList) => acc.concat(curList),
                      [],
                    ).length - 3
                  }`}</BlackTagChip>
                )}
              </Flex>
            )}
          </Section>
          <ColumnImg src="/image/page/myCar/columnLine.svg" />
          <Section width={142}>
            <Text typo="Body3_Regular" palette="DarkGray">
              예상 가격
            </Text>
            <Flex width="auto">
              <Text typo="Heading1_Bold" palette="Black">
                {`${myCarInfo.price.toLocaleString()}`}
              </Text>
              <Text typo="Body3_Regular" palette="Black">
                원
              </Text>
            </Flex>
          </Section>
        </Flex>
        <Flex gap={7} width={304}>
          {myCarUrl.indexOf(currentUrl) != myCarUrl.length - 1 && (
            <>
              {myCarUrl.indexOf(currentUrl) !== 0 ? (
                <div
                  onClick={() => {
                    setLocalData();
                    onClickButton(-1);
                  }}
                >
                  <Button
                    width={121}
                    heightType="medium"
                    backgroundColor="White"
                    typo="Heading4_Bold"
                  >
                    이전
                  </Button>
                </div>
              ) : (
                <Flex width={121}></Flex>
              )}

              <div
                onClick={() => {
                  setLocalData();
                  postData();
                }}
              >
                <Button
                  width={176}
                  heightType="medium"
                  backgroundColor="Primary"
                  typo="Heading4_Bold"
                >
                  다음 단계로
                </Button>
              </div>
            </>
          )}
        </Flex>
      </Flex>
      {isOpenDetailModal && (
        <DetailOptionModal closeModal={() => setIsOpenDetailModal(false)} />
      )}
    </Flex>
  );
};

const Section = styled(Flex)`
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  gap: 6px;
  box-sizing: border-box;
`;

const ColumnImg = styled.img`
  height: 100%;
`;

const BlackTagChip = styled.div`
  display: inline-flex;
  justify-content: center;
  align-items: center;

  max-width: calc(85px - 8px);
  height: 22px;
  padding: 4px 8px;
  border-radius: 6px;

  background-color: ${theme.palette.Black};
  color: ${theme.palette.White};
`;

const SelectOptionText = styled.span`
  ${theme.typo.Body3_Regular};
  color: ${theme.palette.White};

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const ColorCircle = styled.img`
  background-color: ${theme.palette.Primary};
  border-radius: 100px;
  width: 16px;
  height: 16px;
`;
