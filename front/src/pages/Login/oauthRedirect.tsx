import { useEffect } from 'react';
import { useLocation, useNavigate, useSearchParams } from 'react-router-dom';

export const OauthRedirect = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const [searchParams, setSearchParams] = useSearchParams();

  useEffect(() => {
    setSearchParams(location?.search);

    const accessT = searchParams.get('accessToken') || '';
    const refreshT = searchParams.get('refreshToken') || '';

    sessionStorage.setItem('accessToken', accessT);
    sessionStorage.setItem('refreshToken', refreshT);

    navigate('/my-car/trim');
  }, []);

  return <div>...</div>;
};
