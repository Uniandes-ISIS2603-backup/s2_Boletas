--BORRAR LA INFO DE LAS TABLAS
DELETE FROM BOLETAENTITY
DELETE FROM CLIENTEENTITY
DELETE FROM COMENTARIOENTITY
DELETE FROM COMPRAENTITY
DELETE FROM ESPECTACULOENTITY
DELETE FROM SILLAENTITY
DELETE FROM LUGARENTITY
DELETE FROM ORGANIZADORENTITY


--TABLA ESPECTACULO ENTITY
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (100, 'Lost Patrol, The', '2018-02-07T00:14:52Z', 'Fusce congue, diam id ornare imperdiet, sapien urna pretium nisl, ut volutpat sapien arcu sed augue. Aliquam erat volutpat.', 'Hubey Prattington', 'Adventure|Drama|War','https://m.media-amazon.com/images/M/MV5BMTQxMjY0MzgzMl5BMl5BanBnXkFtZTcwNjc0NjQ2OA@@._V1_.jpg');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (200, 'The Little Matchgirl', '2018-01-20T05:56:46Z', 'Aliquam erat volutpat. In congue. Etiam justo. Etiam pretium iaculis justo.', 'Karolina Nower', 'Deporte','https://assets.change.org/photos/3/ds/ot/aLdsOTnBNTvuNHp-800x450-noPad.jpg?1489390974');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (300, 'All the Vermeers in New York', '2017-12-27T16:39:30Z', 'Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo.', 'Bayard Coulson', 'Comedy|Drama|Romance','http://los40es00.epimg.net/los40/imagenes/2017/04/06/album/1491500169_071266_1491562145_album_normal.jpg');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (400, 'Last of the Unjust, The (Dernier des injustes, Le)', '2018-10-17T20:51:26Z', 'Sed accumsan felis. Ut at dolor quis odio consequat varius. Integer ac leo. Pellentesque ultrices mattis odio.', 'Candie Shoorbrooke', 'Documentary','https://i.ebayimg.com/images/g/2TYAAOSwrzhbGXyp/s-l600.jpg');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (500, 'Speed', '2017-11-28T00:18:53Z', 'In tempor, turpis nec euismod scelerisque, quam turpis adipiscing lorem, vitae mattis nibh ligula nec sem. Duis aliquam convallis nunc. Proin at turpis a pede posuere nonummy. Integer non velit. Donec diam neque, vestibulum eget, vulputate ut, ultrices vel, augue.', 'Anne Osbidston', 'Action|Romance|Thriller','https://photos.bandsintown.com/thumb/8669790.jpeg');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (600, 'Eight Below', '2018-04-17T17:52:26Z', 'Donec posuere metus vitae ipsum. Aliquam non mauris. Morbi non lectus. Aliquam sit amet diam in magna bibendum imperdiet. Nullam orci pede, venenatis non, sodales sed, tincidunt eu, felis. Fusce posuere felis sed lacus. Morbi sem mauris, laoreet ut, rhoncus aliquet, pulvinar sed, nisl. Nunc rhoncus dui vel sem. Sed sagittis.', 'Pren Meese', 'Action|Adventure|Drama|Romance','http://i.ebayimg.com/images/g/KmUAAOSw1KNZ1~0m/s-l600.jpg');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (700, 'Mission Congo', '2018-09-23T08:52:30Z', 'In congue. Etiam justo. Etiam pretium iaculis justo. In hac habitasse platea dictumst. Etiam faucibus cursus urna. Ut tellus. Nulla ut erat id mauris vulputate elementum. Nullam varius.', 'Susanne Corbet', 'Documentary','https://i.ebayimg.com/images/g/2DcAAOSwwNtbe0zU/s-l600.jpg');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (800, 'Middle of Nowhere', '2018-06-19T22:53:19Z', 'Nulla nisl. Nunc nisl. Duis bibendum, felis sed interdum venenatis, turpis enim blandit mi, in porttitor pede justo eu massa. Donec dapibus. Duis at velit eu est congue elementum. In hac habitasse platea dictumst. Morbi vestibulum, velit id pretium iaculis, diam erat fermentum justo, nec condimentum neque sapien placerat ante. Nulla justo. Aliquam quis turpis eget elit sodales scelerisque. Mauris sit amet eros.', 'Charmain Ewart', 'Drama|Romance','');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (900, 'Close Your Eyes (Hypnotic) (Doctor Sleep)', '2018-10-14T03:44:52Z', 'Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Proin interdum mauris non ligula pellentesque ultrices. Phasellus id sapien in sapien iaculis congue. Vivamus metus arcu, adipiscing molestie, hendrerit at, vulputate vitae, nisl. Aenean lectus. Pellentesque eget nunc. Donec quis orci eget orci vehicula condimentum. Curabitur in libero ut massa volutpat convallis. Morbi odio odio, elementum eu, interdum eu, tincidunt in, leo.', 'Ricki Bradnum', 'Crime|Thriller','');
insert into ESPECTACULOENTITY (id, nombre, fecha, descripcion, artista, tipo, imagen) values (1000, 'Glenn, the Flying Robot', '2018-05-17T02:23:53Z', 'Vestibulum quam sapien, varius ut, blandit non, interdum in, ante.', 'Nelle Newland', 'Sci-Fi');

--TABLA LUGAR ENTITY
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (201, 'cra 9e 5-29', 'Stang', 77, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (202, 'cra 4k 9-78', 'Teatro de Bogota', 26, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (203, 'cra 6m 0-10', 'Buell', 60, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (204, 'cra 1v 8-27', 'Everett', 77, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (205, 'cra 0l 1-05', 'Hoepker', 70, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (206, 'cra 0z 7-32', 'New Castle', 21, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (207, 'cra 3y 6-49', 'Old Shore', 42, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (208, 'cra 2a 1-93', 'Burrows', 77, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (209, 'cra 3f 0-28', 'Shoshone', 33, 'teatro');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (210, 'cra 3h 0-93', 'Forster', 58, 'coliseo');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (211, 'cra 2f 3-68', 'Sullivan', 58, 'coliseo');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (212, 'cra 1d 8-22', 'Arkansas', 62, 'coliseo');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (213, 'cra 5c 4-45', 'El Campin', 50, 'coliseo');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (214, 'cra 6f 3-56', 'The Turtle', 36, 'coliseo');
insert into LugarEntity (id, direccion, nombre, numsillas, ubicacion) values (215, 'cra 1o 6-10', 'Teatro de México', 49, 'teatro');
