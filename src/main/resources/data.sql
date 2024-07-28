# INSERT INTO address (id, road, number, zip_code)
# VALUES (1, 'Avenida das Cataratas', 'BR 469, Km 18', '85859-899'),
#        (2, 'Avenida das Cataratas', '12.450', '85859-899'),
#        (3, 'Avenida Tancredo Neves', '6.731', '85856-970'),
#        (4, 'Rua Dr. Josivalter Vila Nova', '99', '85867-504'),
#        (5, 'R. Tarobá', '1009', '85851-220'),
#        (6, 'Av. das Cataratas', '8.100', '85853-000'),
#        (7, 'Loren Ipsum', '123', '85853-000'),
#        (8, 'Loren Ipsum', '123', '85853-000'),
#        (9, 'Loren Ipsum', '123', '85853-000'),
#        (10, 'Loren Ipsum', '123', '85853-000'),
#        (11, 'Loren Ipsum', '123', '85853-000'),
#        (12, 'Loren Ipsum', '123', '85853-000'),
#        (13, 'Loren Ipsum', '123', '85853-000'),
#        (14, 'Loren Ipsum', '123', '85853-000'),
#        (15, 'Loren Ipsum', '123', '85853-000'),
#        (16, 'Loren Ipsum', '123', '85853-000'),
#        (17, 'Loren Ipsum', '123', '85853-000'),
#        (18, 'Loren Ipsum', '123', '85853-000'),
#        (19, 'Loren Ipsum', '123', '85853-000'),
#        (20, 'Loren Ipsum', '123', '85853-000'),
#        (21, 'Loren Ipsum', '123', '85853-000'),
#        (22, 'Loren Ipsum', '123', '85853-000'),
#        (23, 'Loren Ipsum', '123', '85853-000'),
#        (24, 'Loren Ipsum', '123', '85853-000'),
#        (25, 'Loren Ipsum', '123', '85853-000'),
#        (26, 'Loren Ipsum', '123', '85853-000'),
#        (27, 'Loren Ipsum', '123', '85853-000'),
#        (28, 'Loren Ipsum', '123', '85853-000'),
#        (29, 'Loren Ipsum', '123', '85853-000'),
#          (30, 'Loren Ipsum', '123', '85853-000'),
#          (31, 'Loren Ipsum', '123', '85853-000'),
#          (32, 'Loren Ipsum', '123', '85853-000'),
#             (33, 'Loren Ipsum', '123', '85853-000'),
#             (34, 'Loren Ipsum', '123', '85853-000'),
#             (35, 'Loren Ipsum', '123', '85853-000'),
#             (36, 'Loren Ipsum', '123', '85853-000'),
#             (37, 'Loren Ipsum', '123', '85853-000');
#
#
# INSERT INTO
#     tourist_point (id, average_value, long_description, name, short_description, interest_point_type)
# VALUES(1, '65.00', 'O Parque Nacional do Iguaçu é uma Unidade de Conservação de Proteção Integral da natureza. Abriga as Cataratas do Iguaçu, uma das Sete Novas Maravilhas da Natureza.',
#        'Parque Nacional do Iguaçu', 'Uma das sete maravilhas do mundo', 'TOURIST_POINT');
#       (2, '45.00', 'O Parque das Aves é um parque particular dedicado à conservação de aves da Mata Atlântica. Localizado próximo ao Parque Nacional do Iguaçu, é uma excelente opção para quem deseja conhecer de perto a fauna da região.',
#        'Parque das Aves', 'Passeio livre em viveiros', 'TOURIST_POINT', 2),
#       (3, '35.00', 'A Hidroeletrica Itaipu Binacional é uma empresa binacional, responsável pela operação da usina hidrelétrica de Itaipu. Localizada no Rio Paraná, é a maior usina hidrelétrica do mundo em geração de energia.',
#        'Itaipu Binacional', 'A maior Hidroelétria do mundo', 'TOURIST_POINT', 3),
#       (4, '0.00', 'O templo budista é um lugar de paz e tranquilidade, onde é possível meditar e refletir sobre a vida. Localizado em uma área verde, o templo é um convite ao relaxamento e à espiritualidade.',
#        'Templo Budista Chen Tien', 'Lugar de paz e tranquilidade.', 'TOURIST_POINT', 4),
#       (5, '35.00', 'O Marco das Três Fronteiras é um monumento que marca a divisa entre Brasil, Argentina e Paraguai. Localizado na cidade de Foz do Iguaçu, o local é um ponto turístico importante e oferece uma vista privilegiada dos três países.',
#          'Marco das Três Fronteiras', 'Divisa entre Brasil, Argentina e Paraguai', 'TOURIST_POINT', 5),
#       (6, '65.00', 'O Museu de Cera Dreamland é um museu de cera localizado em Foz do Iguaçu. O museu conta com réplicas de personalidades famosas do cinema, da música e da história, além de personagens de desenhos animados e super-heróis.',
#           'Museu de Cera Dreamland', 'Réplicas de personalidades famosas', 'TOURIST_POINT', 6);
#
# INSERT INTO
#     restaurant (id, average_value, food_type, name, short_description, interest_point_type,address_id)
# VALUES(7, '150.00', 'Variada', 'Bendito', 'Carnes e massas variadas', 'RESTAURANT', 7),
#       (8, '99.00', 'Pizzaria', 'La Bella Pizza', 'Rodízios e à la carte', 'RESTAURANT', 8),
#       (9, '250.00', 'Hamburgueria', 'Madero', 'Pratos gourmet', 'RESTAURANT', 9),
#       (10, '15.00', 'Lanchonete', 'Tio Gil', 'X picanha por um x salada', 'RESTAURANT', 10),
#       (11, '50.00', 'Churrascaria', 'Churrascaria Rafain', 'Rodízio de carnes', 'RESTAURANT', 11),
#       (12, '80.00', 'Massas', 'Cantina 4 Sorelle', 'Massas e vinhos', 'RESTAURANT', 12),
#       (13, '30.00', 'Italiana', 'Cantinho da Nona', 'Comida caseira', 'RESTAURANT', 13),
#       (14, '40.00', 'Lanches', 'Cantinho do Frango', 'Frango assado', 'RESTAURANT', 14),
#       (15, '25.00', 'Lanches', 'Cantinho do Pastel', 'Pastéis e salgados', 'RESTAURANT', 15),
#       (16, '35.00', 'Doceria', 'Cantinho do Açaí', 'Açaí e lanches', 'RESTAURANT', 16),
#       (17, '40.00', 'Horiental', 'Cantinho do Sushi', 'Sushi e sashimi', 'RESTAURANT', 17),
#       (18, '30.00', 'Fit', 'Cantinho do Tapioca', 'Tapiocas e crepes', 'RESTAURANT', 18);
#
# INSERT INTO
#     hotel (id, average_value, breakfast_included, is_resort, stars_number, name, short_description, interest_point_type,address_id)
# VALUES(19, '299.00', true, true, 4, 'Recanto', 'Lindo complexo logo na entrada da cidade', 'HOTEL', 19),
#       (20, '399.00', true, true, 5, 'Belmond', 'Em frente as quedas das cataratas', 'HOTEL', 20),
#       (21, '59.00', true, true, 2, 'Pousada Chungus', 'Bom, bonito e barato', 'HOTEL', 21),
#       (22, '99.00', true, true, 3, 'Hotel Cataratas', 'Próximo ao Parque Nacional', 'HOTEL', 22),
#       (23, '199.00', true, true, 4, 'Hotel das Cataratas', 'Em frente as quedas', 'HOTEL', 23),
#       (24, '149.00', true, true, 3, 'Hotel Foz', 'Próximo ao centro', 'HOTEL', 24),
#       (25, '79.00', true, true, 2, 'Hotel Centro', 'No coração da cidade', 'HOTEL', 25),
#       (26, '69.00', true, true, 2, 'Hotel Barato', 'Econômico e confortável', 'HOTEL', 26),
#       (27, '49.00', true, true, 1, 'Hotel Econômico', 'O mais barato da cidade', 'HOTEL', 27),
#       (28, '199.00', true, true, 4, 'Hotel Resort', 'Conforto e lazer', 'HOTEL', 28),
#       (29, '299.00', true, true, 5, 'Hotel Resort de Luxo', 'Conforto e lazer', 'HOTEL', 29);
#
# INSERT INTO
#     event (id, average_value, date, long_description, name, short_description, interest_point_type,address_id)
# VALUES (30, '150.00', '31 de Dezembro', 'O reveillon de Foz do Iguaçu é um dos mais famosos do Brasil. A festa acontece na Avenida Paraná, com shows de artistas nacionais e internacionais, queima de fogos e muita animação.',
#         'Reveillon de Foz do Iguaçu', 'Festa de Ano Novo', 'EVENT', 30),
#        (31, '50.00', '15 de Novembro', 'O Festival de Dança de Foz do Iguaçu é um evento que reúne bailarinos e coreógrafos de todo o Brasil. Durante o festival, são realizadas apresentações de dança em diversos estilos, como balé clássico, dança contemporânea e dança de salão.',
#         'Festival de Dança de Foz do Iguaçu', 'Evento cultural', 'EVENT', 31),
#        (32, '200.00', '10 a 14 de Maio', 'Uma seria de shows com vários artitas locais e nacionais em um espaço amplo e confortavel.',
#        'Fespop', 'Festa Popular De Santa Terezinha De Itaipu', 'EVENT', 32),
#        (33, '45.00', '11, 12 e 13 de Outrubro', 'Festa em comemoração ao aniversário de Foz do Iguaçu que conta com shows de artistas de renome nacional, feira de artesanatos e alimentos com a participação das entidades assistenciais',
#        'Fartal', 'Feira de Artesanato e Alimentos de Foz do Iguaçu', 'EVENT', 33);
#
# INSERT INTO
#      experience (id, average_value, long_description, name, short_description, interest_point_type,address_id)
# VALUES(34, '150.00', 'O passeio de barco pelas Cataratas do Iguaçu é uma experiência única e inesquecível. Durante o passeio, é possível ver as quedas d\'água de perto e sentir toda a força e beleza da natureza.',
#        'Passeio de Barco', 'Passeio pelas Cataratas do Iguaçu', 'EXPERIENCE', 34),
#       (35, '50.00', 'O passeio de helicóptero sobre as Cataratas do Iguaçu é uma experiência emocionante e inesquecível. Durante o voo, é possível ver as quedas d\'água de cima e apreciar toda a beleza da região.',
#        'Passeio de Helicóptero', 'Voo sobre as Cataratas do Iguaçu', 'EXPERIENCE', 35),
#       (36, '200.00', 'O passeio de bicicleta pela cidade de Foz do Iguaçu é uma experiência divertida e agradável. Durante o passeio, é possível conhecer os principais pontos turísticos da cidade e apreciar a beleza da região.',
#        'Passeio de Bicicleta', 'Passeio pela cidade de Foz do Iguaçu', 'EXPERIENCE', 36),
#       (37, '45.00', 'O passeio de jipe pela região das Cataratas do Iguaçu é uma experiência emocionante e aventureira. Durante o passeio, é possível explorar trilhas e estradas de terra, conhecer a fauna e a flora da região e apreciar a beleza natural das Cataratas.',
#        'Passeio de Jipe', 'Aventura pelas Cataratas do Iguaçu', 'EXPERIENCE', 37);