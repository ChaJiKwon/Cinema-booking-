INSERT INTO `movies` (`movie_name`, `genre`, `poster_url`, `base_cost`, `cast`, `description`, `duration`, rate)
VALUES
("No man's land",
"Action",
"https://i.ytimg.com/vi/7AJXcuf1uKI/maxresdefault.jpg",
"200000",
"JVevermind",
"Tran Duc Viet lost his parents and striked up his revenge while growing up.",
"1 hour", "3 stars"),

("8-year-old bride",
"Drama, Slice-of-life",
"https://i.ytimg.com/vi/58fqhHTc5A8/maxresdefault.jpg",
"100000", "Pratyusha Banerjee",
"Due to poverty, Anandi had to marry Jadish, a son of a wealthy and nobel family to pay off her parents' debt.",
"2 hours 45 minutes",  "4 stars"),

("Finding Nemo",
"Adventure",
"https://lumiere-a.akamaihd.net/v1/images/p_findingnemo_19752_05271d3f.jpeg",
"125000",
"Albert Brooks, Ellen DeGeneres, Alexander Gould",
"Nemo was suddent caught by the fisherman and his father tries to find ways to save him. In the meantime, Nemo has his own adventure",
"2 hours 30 minutes" , "3 stars"),

("The Thing",
"Action, Horror",
"https://m.media-amazon.com/images/M/MV5BNGViZWZmM2EtNGYzZi00ZDAyLTk3ODMtNzIyZTBjN2Y1NmM1XkEyXkFqcGdeQXVyNTAyODkwOQ@@._V1_.jpg",
"225000",
"Kurt Russell, Keith David, Donald Moffat",
"A team of American researchers found a dog which was running from a team of hunters. The research team decided to adopt the dog, little do they know the secret behind it.",
"2 hours 35 minutes" , "5 stars"),

("The Thing 2: Prequel",
"Action, Horror",
"https://m.media-amazon.com/images/M/MV5BMTMxMjI0MzUyNl5BMl5BanBnXkFtZTcwNjc1NzE5NQ@@._V1_QL75_UX190_CR0,0,190,281_.jpg",
"220000",
"Mary Elizabeth Winstead, Joel Edgerton, Ulrich Thomsen",
"The prequel to the first episode, the story behind how the 'thing' was discovered.",
"2 hours 34 minutes" , "2 stars"),

("John Wick 3: Parabellum",
"Action, Drama",
"https://m.media-amazon.com/images/M/MV5BMDg2YzI0ODctYjliMy00NTU0LTkxODYtYTNkNjQwMzVmOTcxXkEyXkFqcGdeQXVyNjg2NjQwMDQ@._V1_.jpg",
"240000",
"Keanu Reeves, Halle Berry, Ian McShane",
"John tries to earn his freedom while escaping from various of those who thirst for his bounty",
"2 hours 40 minutes" , "3 stars")
ON DUPLICATE KEY UPDATE
    movie_name = VALUES(movie_name),
    genre = VALUES(genre),
    base_cost= VALUES(base_cost),
    description = VALUES(description),
    cast = VALUES(cast),
    duration = VALUES(duration),
    rate = VALUES(rate);



INSERT INTO `theater` (`theater_name`, `a_section`, `b_section`, `vip_section`, `address`, `image_url`, `description`, `plus_cost`)
VALUES
("National Cinema Center",
"30",
"20",
"10",
"87 P. Láng Hạ, Chợ Dừa, Ba Đình, Hà Nội",
"https://api.chieuphimquocgia.com.vn/Content/Image/banner.jpg",
"Open from 8:00 to 22:00", "3.2"),

("CGV Vincom Royal City",
"30",
"20",
"10",
"Vincom Mega Mall, 72A Đ. Nguyễn Trãi, Khu đô thị Royal City, Thanh Xuân, Hà Nội",
"https://statics.vincom.com.vn/vincom-ho/brand/cgv/cgv01.jpeg",
"Open from 8:00 to 22:00" , "2.2"),

("LOTTE CINEMA LANDMARK",
"30",
"20",
"10",
"Tòa Nhà Kaengnam Hà Nội Landmark Tower, E6, Phạm Hùng, Mỹ Đình, Cầu Giấy, Hà Nội",
"https://www.wonderlandvietnam.com.vn/image/catalog/land/lotte-cinema-keangnam(5).jpg",
"Open from 8:00 to 22:00", "4.2"),

("CGV Su Van Hanh",
"30",
"20",
"10",
"Tầng 6, Vạn Hạnh Mall, 11 Đ. Sư Vạn Hạnh, Phường 12, Quận 10, Thành phố Hồ Chí Minh",
"https://iguov8nhvyobj.vcdn.cloud/media/site/cache/1/980x415/b58515f018eb873dafa430b6f9ae0c1e/v/h/vhm-1.png",
"Open from 8:00 to 22:00" , "5.2"),

("Diamond Cinema",
"30",
"20",
"10",
"Tầng 13, Diamond Plaza, 34 Đ. Lê Duẩn, Bến Nghé, Quận 1, Thành phố Hồ Chí Minh",
"https://thegioidienanh.vn/stores/news_dataimages/phuonght/122016/12/09/5030_LC_Diamond_Prestige_3.jpg",
"Open from 8:00 to 22:00" , "1.4"),

("MegaStar Cineplex Hung Vuong Plaza",
"30",
"20",
"10",
"126 Hùng Vương, Tích Sơn, Vĩnh Yên, Vĩnh Phúc",
"https://www.primevn.com/upload/1/products/l_487507015_productimage_s112.jpg",
"Open from 8:00 to 22:00", "2.5")
ON DUPLICATE KEY UPDATE
    theater_name = VALUES(theater_name),
    address = VALUES(address),
    description = VALUES(description),
    a_section = VALUES(a_section),
    b_section = VALUES(b_section),
    vip_section = VALUES(vip_section),
    plus_cost = VALUES(plus_cost);

INSERT INTO `showtime` VALUES ("1", "2024-11-06 19:25", "1");
