USE [BRTADEV]
GO

ALTER TABLE [dbo].[BalePickupTrip] DROP CONSTRAINT [FK_BalePickupTrip_User]
GO

ALTER TABLE [dbo].[BalePickupTrip] DROP CONSTRAINT [FK_BalePickupTrip_Material]
GO

ALTER TABLE [dbo].[BalePickupTrip] DROP CONSTRAINT [FK_BalePickupTrip_CustomerSite1]
GO

ALTER TABLE [dbo].[BalePickupTrip] DROP CONSTRAINT [FK_BalePickupTrip_CustomerSite]
GO

ALTER TABLE [dbo].[BalePickupTrip] DROP CONSTRAINT [FK_BalePickupTrip_CompanyOutlet]
GO

ALTER TABLE [dbo].[BalePickupTrip] DROP CONSTRAINT [FK_BalePickupTrip_BalePickupAssignment]
GO

/****** Object:  Table [dbo].[BalePickupTrip]    Script Date: 12/29/2016 6:08:16 PM ******/
DROP TABLE [dbo].[BalePickupTrip]
GO

/****** Object:  Table [dbo].[BalePickupTrip]    Script Date: 12/29/2016 6:08:16 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[BalePickupTrip](
	[TripId] [int] NOT NULL,
	[BalePickupAssignmentId] [int] NOT NULL,
	[UserId] [int] NOT NULL,
	[BuyCustomerSiteId] [int] NOT NULL,
	[BuyLegacyVendorId] [nvarchar](50) NULL,
	[MaterialId] [int] NOT NULL,
	[CompanyOutletId] [int] NULL,
	[BalesPicked] [smallint] NOT NULL,
	[BalesRemaining] [smallint] NOT NULL,
	[PickupDate] [smalldatetime] NOT NULL,
	[AvgBaleWeight] [float] NOT NULL,
	[PickedBaleTotalWeight] [float] NOT NULL,
	[SellCustomerSiteId] [int] NOT NULL,
	[SellLegacyVendorId] [nvarchar](50) NULL,
	[BOL] [int] NOT NULL,
	[Incident] [bit] NOT NULL,
	[IncidentType] [nvarchar](50) NULL,
	[Comments] [nvarchar](225) NULL,
	[CreateDate] [smalldatetime] NOT NULL,
	[UpdatedAt] [smalldatetime] NOT NULL,
	[Enabled] [bit] NOT NULL,
	[Field1] [nvarchar](50) NULL,
	[Field2] [nvarchar](50) NULL,
 CONSTRAINT [PK_BalePickupTrip] PRIMARY KEY CLUSTERED 
(
	[TripId] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

ALTER TABLE [dbo].[BalePickupTrip]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTrip_BalePickupAssignment] FOREIGN KEY([BalePickupAssignmentId])
REFERENCES [dbo].[BalePickupAssignment] ([BalePickupAssignmentId])
GO

ALTER TABLE [dbo].[BalePickupTrip] CHECK CONSTRAINT [FK_BalePickupTrip_BalePickupAssignment]
GO

ALTER TABLE [dbo].[BalePickupTrip]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTrip_CompanyOutlet] FOREIGN KEY([CompanyOutletId])
REFERENCES [dbo].[CompanyOutlet] ([CompanyOutletId])
GO

ALTER TABLE [dbo].[BalePickupTrip] CHECK CONSTRAINT [FK_BalePickupTrip_CompanyOutlet]
GO

ALTER TABLE [dbo].[BalePickupTrip]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTrip_CustomerSite] FOREIGN KEY([BuyCustomerSiteId])
REFERENCES [dbo].[CustomerSite] ([CustomerSiteId])
GO

ALTER TABLE [dbo].[BalePickupTrip] CHECK CONSTRAINT [FK_BalePickupTrip_CustomerSite]
GO

ALTER TABLE [dbo].[BalePickupTrip]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTrip_CustomerSite1] FOREIGN KEY([SellCustomerSiteId])
REFERENCES [dbo].[CustomerSite] ([CustomerSiteId])
GO

ALTER TABLE [dbo].[BalePickupTrip] CHECK CONSTRAINT [FK_BalePickupTrip_CustomerSite1]
GO

ALTER TABLE [dbo].[BalePickupTrip]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTrip_Material] FOREIGN KEY([MaterialId])
REFERENCES [dbo].[Material] ([MaterialId])
GO

ALTER TABLE [dbo].[BalePickupTrip] CHECK CONSTRAINT [FK_BalePickupTrip_Material]
GO

ALTER TABLE [dbo].[BalePickupTrip]  WITH CHECK ADD  CONSTRAINT [FK_BalePickupTrip_User] FOREIGN KEY([UserId])
REFERENCES [dbo].[User] ([UserId])
GO

ALTER TABLE [dbo].[BalePickupTrip] CHECK CONSTRAINT [FK_BalePickupTrip_User]
GO

/***** ADD CHILDTRIPID COLUMN TO THE TABLE ****/
ALTER TABLE [dbo].[BalePickupTrip] ADD [ChildTripId] [int] NOT NULL
GO

/*** DROP THE CHILDTRIPID COLUMN FROM THE TABLE *****/
ALTER TABLE [dbo].[BalePickupTrip] DROP COLUMN [ChildTripId]
GO