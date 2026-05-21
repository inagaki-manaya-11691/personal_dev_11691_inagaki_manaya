-- categories テーブルにデータを挿入
INSERT INTO categories ( name) VALUES
( '主食'),
( '肉'),
( '魚'),
( '青果'),
( '飲み物'),
( '調味料'),
( 'その他');

-- users テーブルにデータを挿入
INSERT INTO users ( email, name, password) VALUES
( 'tanaka@aaa.com', '田中太郎', 'test123'),
( 'suzuki@aaa.com', '鈴木一郎', 'test456');

--- foods テーブルにデータを挿入（田中太郎）
INSERT INTO foods (category_id, users_id, foods_name, limits, createAt, limits_date, quantity) VALUES
(1, 1, '米', false, '2026-05-20 15:00:00', '2026-05-25', 1),
(2, 1, '肉', true, '2026-05-20 15:00:01', '2026-05-25', 1),
(3, 1, '鮭', true, '2026-05-20 15:00:02', '2026-05-25', 1),
(4, 1, 'りんご', true, '2026-05-20 15:00:03', '2026-05-25', 1),
(5, 1, '水', false, '2026-05-20 15:00:04', '2026-05-25', 1),
(6, 1, '醤油', true, '2026-05-20 15:00:05', '2026-05-25', 1),
(7, 1, 'ドーナツ', true, '2026-05-20 15:00:06', '2026-05-25', 1);

-- foods テーブルにデータを挿入（鈴木一郎）
INSERT INTO foods (category_id, users_id, foods_name, limits, createAt, limits_date, quantity) VALUES
(1, 2, '鈴木の米', false, '2026-05-20 15:00:00', '2026-05-25', 1);