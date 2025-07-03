import React from 'react';
import { Container, Card, Button } from 'react-bootstrap';

function App() {
  return (
    <div className="App">
      <Container className="mt-5">
        <Card>
          <Card.Header className="bg-primary text-white">
            Sistema de Denúncias
          </Card.Header>
          <Card.Body>
            <h2 className="mb-4">Bem-vindo ao Sistema de Denúncias</h2>
            <p>Este é um sistema para registro e acompanhamento de denúncias.</p>
            <Button variant="primary" size="lg">
              Fazer uma Denúncia
            </Button>
          </Card.Body>
        </Card>
      </Container>
    </div>
  );
}

export default App;
