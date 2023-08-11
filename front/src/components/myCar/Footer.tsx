import { Flex, Text, Button } from '@components/common';
import { useMyCar } from '@contexts/MyCarContext';
import styled from '@emotion/styled';
import { myCarUrl } from '@pages/MyCar';
import { theme } from '@styles/theme';
import { TrimUrl, apiInstance } from '@utils/api';
import { Dispatch, SetStateAction, useState } from 'react';
import { useNavigate } from 'react-router-dom';

interface footerProps {
  currentUrl: string;
  setCurrentUrl: Dispatch<SetStateAction<string>>;
}

export const Footer = ({ currentUrl, setCurrentUrl }: footerProps) => {
  const navigate = useNavigate();
  const { myCarInfo } = useMyCar();

  const [archivingId, setArchivingId] = useState(null);

  const onClickButton = async (moveNum: number) => {
    const nextIdx = myCarUrl.indexOf(currentUrl) + moveNum;
    const nextUrl = myCarUrl[nextIdx];

    navigate(nextUrl);
    setCurrentUrl(nextUrl);
  };

  const postData = async () => {
    switch (currentUrl) {
      case '/my-car/trim':
        try {
          const archivingId = await apiInstance({
            url: `${TrimUrl.MODELS}?carOptionId=${myCarInfo.trim.type?.id}`,
            method: 'POST',
            bodyData: JSON.stringify({
              userId: 1,
              archivingId: null,
              carOptionId: myCarInfo.trim.type?.id,
            }),
          });
          setArchivingId(() => archivingId);
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
              userId: 1,
              archivingId: archivingId,
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
              userId: 1,
              archivingId: archivingId,
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
              userId: 1,
              archivingId: archivingId,
              carOptionId: myCarInfo.trim.traction?.id,
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
      <Flex width={1280} gap={30}>
        <Flex>
          <Section width={175}>
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
          <Section width={220}>
            <Text typo="Body3_Regular" palette="DarkGray">
              선택 색상
            </Text>
            <Flex justify="start" gap={5}>
              <Text typo="Body3_Medium" palette="Black">
                외장
              </Text>
              <Flex
                backgroundColor="Primary"
                borderRadius="100px"
                width={16}
                height={16}
              />
              <Text typo="Body3_Regular">
                {myCarInfo.color.exteriorColor?.name}
              </Text>
            </Flex>
            <Flex justify="start" gap={5}>
              <Text typo="Body3_Medium" palette="Black">
                내장
              </Text>
              <Flex
                backgroundColor="Primary"
                borderRadius="100px"
                width={16}
                height={16}
              />
              <Text typo="Body3_Regular">
                {myCarInfo.color.interiorColor?.name}
              </Text>
            </Flex>
          </Section>
          <ColumnImg src="/image/page/myCar/columnLine.svg" />
          <Section width={290}>
            <Text typo="Body3_Regular" palette="DarkGray">
              선택 옵션
            </Text>
            <Flex
              gap={8}
              justify="flex-start"
              align="center"
              style={{ overflow: 'auto' }}
            >
              {myCarInfo.selectedOption.map((option, idx) => (
                <BlackTagChip key={`chip_${idx}`}>{option.name}</BlackTagChip>
              ))}
            </Flex>
          </Section>

          <ColumnImg src="/image/page/myCar/columnLine.svg" />
          <Section width={170}>
            <Text typo="Body3_Regular" palette="DarkGray">
              예상 가격
            </Text>
            <Flex width="auto">
              <Text typo="Heading1_Bold" palette="Black">
                {`${myCarInfo.price.toLocaleString('ko-KR')}`}
              </Text>
              <Text typo="Body3_Regular" palette="Black">
                원
              </Text>
            </Flex>
          </Section>
        </Flex>
        <Flex gap={7} width="auto">
          {myCarUrl.indexOf(currentUrl) != myCarUrl.length - 1 && (
            <>
              {myCarUrl.indexOf(currentUrl) !== 0 ? (
                <div onClick={() => onClickButton(-1)}>
                  <Button
                    width={121}
                    heightType="medium"
                    backgroundColor="White"
                  >
                    <Text palette="Primary" typo="Heading4_Bold">
                      이전
                    </Text>
                  </Button>
                </div>
              ) : (
                <Flex width={121}></Flex>
              )}

              <div onClick={() => postData()}>
                <Button
                  width={176}
                  heightType="medium"
                  backgroundColor="Primary"
                >
                  <Text palette="White" typo="Heading4_Bold">
                    다음 단계로
                  </Text>
                </Button>
              </div>
            </>
          )}
        </Flex>
      </Flex>
    </Flex>
  );
};

const Section = styled(Flex)`
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;

  gap: 6px;
  padding: 0 0 0 22px;
  box-sizing: border-box;
`;

const ColumnImg = styled.img`
  height: 100%;
`;

const BlackTagChip = styled(Flex)`
  width: auto;
  height: 30px;

  padding: 0 8px;
  background-color: ${theme.palette.Black};
  color: ${theme.palette.White};
  ${theme.typo.Body3_Regular}
  border-radius: 8px;

  white-space: nowrap;
`;
