USE [BRTADEV]
GO

/****** Object:  Table [dbo].[SysUser]    Script Date: 12/21/2016 2:52:32 PM ******/
DROP TABLE [dbo].[SysUser]
GO

/****** Object:  Table [dbo].[SysUser]    Script Date: 12/21/2016 2:52:32 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[SysUser](
	[SysUserId] [int] NOT NULL,
	[Surname] [nvarchar](25) NOT NULL,
	[ForeName] [nvarchar](25) NULL,
	[SysUserName] [nvarchar](225) NULL,
	[Email] [nvarchar](50) NULL,
	[TelNo] [nvarchar](30) NULL,
	[MobileTelNo] [nvarchar](30) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[UpdaetdBy] [nvarchar](25) NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_SysUser] PRIMARY KEY CLUSTERED 
(
	[SysUserId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

